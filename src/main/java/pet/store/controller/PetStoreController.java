package pet.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
public class PetStoreController {

    private final PetStoreService petStoreService;

    @Autowired
    public PetStoreController(PetStoreService petStoreService) {
        this.petStoreService = petStoreService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
        return petStoreService.savePetStore(petStoreData);
    }
    
    @PutMapping("/{storeId}")
    public PetStoreData updatePetStore(@PathVariable Long storeId, @RequestBody PetStoreData petStoreData) {
        return petStoreService.updatePetStore(storeId, petStoreData);
    }
    
    @PostMapping("/{petStoreId}/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreEmployee addEmployeeToPetStore(@PathVariable Long petStoreId, @RequestBody PetStoreEmployee employee) {
        return petStoreService.addEmployeeToPetStore(petStoreId, employee);
    }
    
    @PostMapping("/{petStoreId}/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreCustomer addCustomerToPetStore(@PathVariable Long petStoreId, @RequestBody PetStoreCustomer customer) {
        return petStoreService.addCustomerToPetStore(petStoreId, customer);
    }
    
    @GetMapping
    public List<PetStoreData> getAllPetStores() {
        return petStoreService.retrieveAllPetStores();
    }
    
    @GetMapping("/{petStoreId}")
    public PetStoreData getPetStoreById(@PathVariable Long petStoreId) {
        return petStoreService.retrievePetStoreById(petStoreId);
    }
    
    @DeleteMapping("/{petStoreId}")
    public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
        System.out.println("Deleting pet store with ID: " + petStoreId);
        petStoreService.deletePetStoreById(petStoreId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Pet store with ID " + petStoreId + " deleted successfully.");
        return response;
    }



    
}



