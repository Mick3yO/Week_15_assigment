package pet.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;
import pet.store.dao.CustomerDao;

@Service
public class PetStoreService {

    private final PetStoreDao petStoreDao;
    private final EmployeeDao employeeDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    public PetStoreService(PetStoreDao petStoreDao, EmployeeDao employeeDao) {
        this.petStoreDao = petStoreDao;
        this.employeeDao = employeeDao;
    }

    public PetStoreData savePetStore(PetStoreData petStoreData) {
        Long petStoreId = petStoreData.getPetStoreId();
        PetStore petStore = (petStoreId == null)
                ? findOrCreatePetStore()
                : findPetStoreById(petStoreId);
        copyPetStoreFields(petStore, petStoreData);
        PetStore savedPetStore = petStoreDao.save(petStore);
        return new PetStoreData(savedPetStore);
    }

    private PetStore findOrCreatePetStore() {
        return new PetStore();
    }

    private PetStore findPetStoreById(Long petStoreId) {
        return petStoreDao.findById(petStoreId)
                .orElseThrow(() -> new NoSuchElementException("Pet Store with ID " + petStoreId + " not found."));
    }

    private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
        petStore.setPetStoreName(petStoreData.getPetStoreName());
        petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
        petStore.setPetStoreCity(petStoreData.getPetStoreCity());
        petStore.setPetStoreState(petStoreData.getPetStoreState());
        petStore.setPetStoreZip(petStoreData.getPetStoreZip());
        petStore.setPetStorePhone(petStoreData.getPetStorePhone());
    }
    
    public PetStoreData updatePetStore(Long storeId, PetStoreData petStoreData) {
        PetStore existingPetStore = findPetStoreById(storeId);
        copyPetStoreFields(existingPetStore, petStoreData);
        PetStore updatedPetStore = petStoreDao.save(existingPetStore);
        return new PetStoreData(updatedPetStore);
    }
    
    public PetStoreEmployee addEmployeeToPetStore(Long petStoreId, PetStoreEmployee employee) {
        PetStore petStore = findPetStoreById(petStoreId);
        Employee newEmployee = new Employee();
        newEmployee.setEmployeeFirstName(employee.getEmployeeFirstName());
        newEmployee.setEmployeeLastName(employee.getEmployeeLastName());
        newEmployee.setEmployeePhone(employee.getEmployeePhone());
        newEmployee.setEmployeeJobTitle(employee.getEmployeeJobTitle()); // Set employee job title
        newEmployee.setPetStore(petStore);
        Employee savedEmployee = employeeDao.save(newEmployee);

        PetStoreEmployee newPetStoreEmployee = new PetStoreEmployee();
        newPetStoreEmployee.setEmployeeId(savedEmployee.getEmployeeId());
        newPetStoreEmployee.setPetStoreId(petStoreId);
        newPetStoreEmployee.setEmployeeFirstName(savedEmployee.getEmployeeFirstName());
        newPetStoreEmployee.setEmployeeLastName(savedEmployee.getEmployeeLastName());
        newPetStoreEmployee.setEmployeePhone(savedEmployee.getEmployeePhone());
        newPetStoreEmployee.setEmployeeJobTitle(savedEmployee.getEmployeeJobTitle()); // Set employee job title
        newPetStoreEmployee.setPetStoreName(petStore.getPetStoreName()); // Set pet store name

        return newPetStoreEmployee;
    }
    public PetStoreCustomer addCustomerToPetStore(Long petStoreId, PetStoreCustomer customer) {
        PetStore petStore = findPetStoreById(petStoreId);

        Customer newCustomer = new Customer();
        newCustomer.setCustomerFirstName(customer.getCustomerFirstName());
        newCustomer.setCustomerLastName(customer.getCustomerLastName());
        newCustomer.setCustomerEmail(customer.getCustomerEmail());

        // Save the new customer to generate the customer ID
        Customer savedCustomer = customerDao.save(newCustomer);

        petStore.getCustomers().add(savedCustomer);
        PetStore savedPetStore = petStoreDao.save(petStore);

        PetStoreCustomer newPetStoreCustomer = new PetStoreCustomer();
        newPetStoreCustomer.setCustomerId(savedCustomer.getCustomerId());
        newPetStoreCustomer.setCustomerFirstName(savedCustomer.getCustomerFirstName());
        newPetStoreCustomer.setCustomerLastName(savedCustomer.getCustomerLastName());
        newPetStoreCustomer.setCustomerEmail(savedCustomer.getCustomerEmail());

        return newPetStoreCustomer;
    }
    public List<PetStoreData> retrieveAllPetStores() {
        List<PetStore> petStores = petStoreDao.findAll();
        List<PetStoreData> petStoreDataList = new ArrayList<>();

        for (PetStore petStore : petStores) {
            PetStoreData petStoreData = new PetStoreData();
            petStoreData.setPetStoreId(petStore.getPetStoreId());
            petStoreData.setPetStoreName(petStore.getPetStoreName());
            petStoreData.setPetStoreAddress(petStore.getPetStoreAddress());
            petStoreData.setPetStoreCity(petStore.getPetStoreCity());
            petStoreData.setPetStoreState(petStore.getPetStoreState());
            petStoreData.setPetStoreZip(petStore.getPetStoreZip());

            // Add the petStoreData object to the list
            petStoreDataList.add(petStoreData);
        }

        return petStoreDataList;
    }
    
    public PetStoreData retrievePetStoreById(Long petStoreId) {
        PetStore petStore = findPetStoreById(petStoreId);
        return new PetStoreData(petStore);
    }

    public void deletePetStoreById(Long petStoreId) {
        petStoreDao.deleteById(petStoreId);
    }






}

