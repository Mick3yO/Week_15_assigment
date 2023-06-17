package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PetStoreEmployee {
	
    private Long employeeId;
	private String EmployeeFirstName;
	private String EmployeeLastName;
	private String EmployeePhone;
	private String EmployeeJobTitle;
	private String petStoreName;
	private Long petStoreId;
	
    public void setPetStoreId(Long petStoreId) {
        this.petStoreId = petStoreId;
    }
	
}
