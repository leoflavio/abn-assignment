package com.assignment.abn.service;

import com.assignment.abn.exception.RecipeNotFoundException;
import com.assignment.abn.exception.RecipeSaveCommandException;
import com.assignment.abn.exception.RecipeUpdateCommandException;
import com.assignment.abn.model.Recipe;
import com.assignment.abn.model.RecipeCommand;
import com.assignment.abn.mapper.RecipeMapper;
import com.assignment.abn.model.RecipeQuery;
import com.assignment.abn.repository.RecipeRepository;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;

    @Override
    public Long save(RecipeCommand recipeCommand) {
        var recipe = RecipeMapper.INSTANCE.RecipeCommandToRecipe(recipeCommand);
        try {
            return recipeRepository.save(recipe).getId();
        } catch (Exception ex){
            log.error("Exception thrown during saving recipe", ex);
            throw new RecipeSaveCommandException();
        }
    }

    @Override
    public Long update(RecipeCommand recipeCommand) {
        var recipeOptional = recipeRepository.findById(recipeCommand.id());
        recipeOptional.ifPresentOrElse(recipe -> {
            RecipeMapper.INSTANCE.updateRecipe(recipe, recipeCommand);
            try {
                recipeRepository.save(recipe);
            } catch (Exception ex){
                log.error("Exception thrown during update recipe", ex);
                throw new RecipeUpdateCommandException(recipeCommand.id());
            }
        }, () -> {throw new RecipeNotFoundException(recipeCommand.id());});
        return recipeCommand.id();
    }

    @Override
    public void delete(Long id) {
        var recipeOptional = recipeRepository.findById(id);
        recipeOptional.ifPresentOrElse(recipeRepository::delete, () -> {throw new RecipeNotFoundException(id);});
    }

    @Override
    public RecipeQuery getRecipeById(Long id) {
        return recipeRepository.findById(id).map(RecipeMapper.INSTANCE::RecipeToRecipeQuery).orElseThrow(() -> {throw new RecipeNotFoundException(id);});
    }

    @Override
    public List<RecipeQuery> listAll() {
        return recipeRepository.findAll().stream().map(RecipeMapper.INSTANCE::RecipeToRecipeQuery).toList();
    }

    @Override
    public List<RecipeQuery> searchRecipe(Optional<String> name,
                                     Optional<Integer> numServing,
                                     Optional<String> instructions,
                                     Optional<Boolean> vegetarian,
                                     Optional<Set<String>> ingredientsIn,
                                     Optional<Set<String>> ingredientsOut) {

        var recipeList = recipeRepository.findAll(getRecipeQuery(name, numServing, instructions, vegetarian, ingredientsIn, ingredientsOut));
//        q.ingredientsOut().ifPresent(ingredients -> ingredients.forEach(ingredient -> recipeList.removeIf(recipe -> recipe.getIngredients().contains(ingredient))));

        var result = recipeList.stream().map(RecipeMapper.INSTANCE::RecipeToRecipeQuery).toList();
        return result;
    }

    private Specification<Recipe> getRecipeQuery(Optional<String> nameOptional,
                                                 Optional<Integer> numServingOptional,
                                                 Optional<String> instructionsOptional,
                                                 Optional<Boolean> vegetarianOptional,
                                                 Optional<Set<String>> ingredientsInOptional,
                                                 Optional<Set<String>> ingredientsOutOptional) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            nameOptional.ifPresent(name -> predicates.add(criteriaBuilder.like(root.get("name"), "%"+name+"%")));
            numServingOptional.ifPresent(numServing -> predicates.add(criteriaBuilder.equal(root.get("numServing"), numServing)));
            instructionsOptional.ifPresent(instructions -> predicates.add(criteriaBuilder.like(root.get("instructions"), "%"+instructions+"%")));
            vegetarianOptional.ifPresent(vegetarian -> predicates.add(criteriaBuilder.equal(root.get("vegetarian"), vegetarian)));
            ingredientsInOptional.ifPresent(ingredientsIn -> predicates.add(criteriaBuilder.in(root.join("ingredients", JoinType.LEFT)).value(ingredientsIn)));
            ingredientsOutOptional.ifPresent(ingredientsOut -> ingredientsOut.stream().map(ingredient -> criteriaBuilder.isNotMember(ingredient, root.get("ingredients"))).forEach(predicates::add));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
