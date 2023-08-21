package pl.mm.foodemo.api.controller.owner_panel;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.MealDTO;
import pl.mm.foodemo.api.dto.mapper.MealMapperDTO;
import pl.mm.foodemo.business.service.MealService;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.domain.Meal;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.domain.exception.WrongFileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static pl.mm.foodemo.api.controller.owner_panel.RestaurantPanelController.PANEL;

@Controller
@AllArgsConstructor
@Validated
@RequestMapping(PANEL)
public class EditRestaurantMenuController {

    private static final String RESTAURANT_ID_EDIT_MENU = "/restaurant/{id}/edit/menu";
    private static final String ADD = "/add";

    private final RestaurantService restaurantService;
    private final MealService mealService;

    private final MealMapperDTO mealMapper;

    private static String getImageName(MultipartFile image) throws IOException {
        if (!image.isEmpty()) {
            if (Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path imagePath = Paths.get("src/main/resources/static/upload_images/", fileName);
                Files.write(imagePath, image.getBytes());

                return fileName;
            } else {
                throw new WrongFileException("Wrong image file");
            }
        }
        return "";
    }

    @GetMapping(value = RESTAURANT_ID_EDIT_MENU)
    public ModelAndView editRestaurantMenuPage(
            @PathVariable("id") final Long id
    ) {

        Restaurant restaurant = restaurantService.findById(id);
        List<MealDTO> restaurantAllMeals = mealService.findAllByRestaurantId(restaurant).stream().map(mealMapper::map).toList();


        Map<String, Object> modelData = Map.of(
                "restaurantId", restaurant.getRestaurantId(),
                "restaurantName", restaurant.getName(),
                "restaurantAllMealDTOs", restaurantAllMeals,
                "mealDTO", MealDTO.builder().build()
        );
        return new ModelAndView("owner_panel/restaurant_edit_menu", modelData);
    }

    @PostMapping(RESTAURANT_ID_EDIT_MENU + ADD)
    public String addMealToMenuPage(
            @PathVariable("id") final Long id,
            @Valid @ModelAttribute("mealDTO") MealDTO mealDTO,
            BindingResult result,
            @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer,
            @RequestParam("image") final MultipartFile image
    ) throws IOException {
        if (result.hasErrors()) {
            return "error";
        }

        String imageName = getImageName(image);

        if (!imageName.equals("")) {
            mealDTO = mealDTO.withImageName(imageName);
        }

        Restaurant restaurant = restaurantService.findById(id);
        Meal meal = mealMapper.map(mealDTO).withRestaurant(restaurant);

        mealService.save(meal);

        return "redirect:" + referrer;
    }

}
