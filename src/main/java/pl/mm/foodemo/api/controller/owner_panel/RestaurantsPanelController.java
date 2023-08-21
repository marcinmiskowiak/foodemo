package pl.mm.foodemo.api.controller.owner_panel;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.AddressDTO;
import pl.mm.foodemo.api.dto.RestaurantDTO;
import pl.mm.foodemo.api.dto.UserDTO;
import pl.mm.foodemo.api.dto.mapper.AddressMapperDTO;
import pl.mm.foodemo.api.dto.mapper.RestaurantMapperDTO;
import pl.mm.foodemo.api.dto.mapper.UserMapperDTO;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.business.service.UserService;
import pl.mm.foodemo.domain.User;
import pl.mm.foodemo.infrastructure.security.CustomUserDetails;

import java.util.List;
import java.util.Map;

import static pl.mm.foodemo.api.controller.owner_panel.RestaurantPanelController.PANEL;

@Controller
@AllArgsConstructor
@RequestMapping(PANEL)
public class RestaurantsPanelController {

    private final RestaurantService restaurantService;
    private final UserService userService;

    private final AddressMapperDTO addressMapper;
    private final UserMapperDTO userMapper;
    private final RestaurantMapperDTO restaurantMapper;


    @GetMapping
    public ModelAndView ownerPanelPage(
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ) {
        User user = userService.findUserById(userDetails.getUser().getUserId());
        AddressDTO userAddress = addressMapper.map(user.getAddress());
        UserDTO userDTO = userMapper.map(user);

        List<RestaurantDTO> restaurantDTOList = restaurantService.findByOwnerId(userDTO.getUserId()).stream().map(restaurantMapper::map).toList();

        return new ModelAndView("owner_panel/panel", Map.of(
                "addressDTO", userAddress,
                "userDTO", userDTO,
                "restaurantDTOs", restaurantDTOList
        ));
    }

}
