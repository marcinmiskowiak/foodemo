package pl.mm.foodemo.api.controller.user_panel;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.AddressDTO;
import pl.mm.foodemo.api.dto.UserDTO;
import pl.mm.foodemo.api.dto.UserEditProfileDTO;
import pl.mm.foodemo.api.dto.mapper.AddressMapperDTO;
import pl.mm.foodemo.api.dto.mapper.UserMapperDTO;
import pl.mm.foodemo.business.service.AddressService;
import pl.mm.foodemo.business.service.UserService;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.domain.User;
import pl.mm.foodemo.infrastructure.security.CustomUserDetails;

import java.util.Map;

import static pl.mm.foodemo.api.controller.user_panel.EditProfileController.PROFILE;
import static pl.mm.foodemo.api.controller.user_panel.RestaurantController.USER;

@Controller
@AllArgsConstructor
@Validated
@RequestMapping(USER + PROFILE)
public class EditProfileController {

    static final String PROFILE = "/profile";
    private static final String EDIT = "/edit";
    private static final String EDIT_ADDRESS = "/edit_address";
    private static final String EDIT_CONTACT = "/edit_contact";

    private final AddressService addressService;
    private final UserService userService;
    private final AddressMapperDTO addressMapper;
    private final UserMapperDTO userMapper;

    @GetMapping( EDIT)
    public ModelAndView editProfilePage(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        AddressDTO userAddress = addressMapper.map(
                addressService.findAddressById(userDetails.getUser().getAddress().getAddressId())
        );
        UserDTO userDTO = userMapper.map(
                userService.findUserById(userDetails.getUser().getUserId())
        );

        return new ModelAndView(
                "user_panel/edit_profile",
                Map.of(
                        "addressDTO", userAddress,
                        "userDTO", userDTO
                )
        );
    }


    @PutMapping(EDIT_ADDRESS)
    public String editAddress(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @ModelAttribute("addressDTO") AddressDTO addressDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "error";
        }

        Address userUpdatedAddress = addressMapper.map(addressDTO).withAddressId(userDetails.getUser().getAddress().getAddressId());


        addressService.saveAddress(userUpdatedAddress);

        return "redirect:/user/restaurants";
    }

    @PutMapping(EDIT_CONTACT)
    public String editContactDetails(
            @AuthenticationPrincipal final CustomUserDetails userDetails,
            @Valid @ModelAttribute("userDTO") final UserEditProfileDTO userDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "error";
        }

        User user = userService.findUserById(userDetails.getUser().getUserId())
                .withEmail(userDTO.getEmail())
                .withPhone(userDTO.getPhone());

        userService.saveUser(user);

        return "redirect:/user/restaurants";
    }
}
