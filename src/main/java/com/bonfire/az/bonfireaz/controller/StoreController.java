package com.bonfire.az.bonfireaz.controller;

import com.bonfire.az.bonfireaz.exception.UserServiceException;
import com.bonfire.az.bonfireaz.model.entity.Store;
import com.bonfire.az.bonfireaz.model.entity.XUser;
import com.bonfire.az.bonfireaz.model.request.StoreRq;
import com.bonfire.az.bonfireaz.model.response.OperationModel;
import com.bonfire.az.bonfireaz.model.response.StoreDto;
import com.bonfire.az.bonfireaz.repo.StoreRepo;
import com.bonfire.az.bonfireaz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bonfire.az.bonfireaz.controller.OperationName.CREATE_STORE;
import static com.bonfire.az.bonfireaz.controller.OperationName.UPDATE_STORE;
import static com.bonfire.az.bonfireaz.model.response.OperationStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
//@CrossOrigin(origins = "*")
public class StoreController {

    private final StoreRepo storeRepo;
    private final UserService userService;
    private final ModelMapper mapper;

    @GetMapping("{userId}")
    public List<StoreDto> getAllStores(@PathVariable String userId) {
        XUser user = userService.findXUserByUserId(userId);
        List<Store> userStores = storeRepo.getAllByUser(user);
        return userStores.stream()
                .map(store -> mapper.map(store, StoreDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("{userId}")
    public OperationModel createOrUpdateStore(@PathVariable String userId,
                                              @RequestBody StoreRq storeRq) {
        XUser user = userService.findXUserByUserId(userId);
        if (storeRq.getId() != null) {
            Optional<Store> opStore = storeRepo.findById(storeRq.getId());
            opStore.map(store -> {
                store.setUpdatedDate(LocalDate.now());
                store.setTitle(storeRq.getTitle());
                store.setSubtitle(storeRq.getSubtitle());
                store.setAbout(storeRq.getAbout());
                storeRepo.save(store);
                return store;
            }).orElseThrow(()-> new UserServiceException(String.format("no stores found with id: %s", storeRq.getId())));
            return new OperationModel(SUCCESS, UPDATE_STORE);
        } else {
            Store newStore = Store.builder()
                    .createdDate(LocalDate.now())
                    .updatedDate(LocalDate.now())
                    .title(storeRq.getTitle())
                    .subtitle(storeRq.getSubtitle())
                    .about(storeRq.getAbout())
                    .user(user)
                    .build();
            storeRepo.save(newStore);
            return new OperationModel(SUCCESS, CREATE_STORE);
        }
    }

}
