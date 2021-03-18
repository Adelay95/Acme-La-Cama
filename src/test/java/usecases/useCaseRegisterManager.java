
package usecases;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ManagerService;
import utilities.AbstractTest;
import domain.Brand;
import domain.CreditCard;
import domain.Manager;
import forms.FormActor;
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class useCaseRegisterManager extends AbstractTest {

	@Autowired
	private ManagerService	managerService;

    //Register to the system as a manager.
	//Servicios

	@Test
	public void driver() {
		final Object testingData[][] =  {

			{
				//Este es un caso de prueba normal, para comprobar la funcionalidad general
				null, "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernamePrueba2", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406",false,
				"Calle Echegaray", null
			},{
				//En este caso probamos con una tarjeta de crédito pasada, debería dar error
				null, "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"username22", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2016","406",false,
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//En este caso probamos con contraseñas que no coinciden
				null, "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"username32", "contraseña", "contraseña2", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406",false,
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//En este caso probamos con un cVV inválido, debería dar error
				null, "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernameAgore32", "contraseña", "contraseña", true,
				"holderName",Brand.AMEX,"5555555555554444","12","2020","56",false,
				"Calle Echegaray", ConstraintViolationException.class
			}
			,{
				//En este caso probamos con un campo vacío, debería dar error
				null, "Francisco Javier", "","frahiggal@us.es","660974252",
				"usernamePrueba123", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406",false,
				"Calle Echegaray", DataIntegrityViolationException.class
			}
			
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], 
				(String)testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],(Boolean) testingData[i][8],(String) testingData[i][9],
				(Brand) testingData[i][10],(String) testingData[i][11],new Integer((String)testingData[i][12]),new Integer((String)testingData[i][13]),
				new Integer((String)testingData[i][14]),(Boolean) testingData[i][15],(String) testingData[i][16],(Class<?>) testingData[i][17]);
	}
	
	protected void template(final String username, final String name,final String surname, final String email,final String phoneNumber,final String usernameString
		, final String password1, final String password2, final Boolean confirmed, final String holderName, final Brand brandName
		, final String accountNumber, final Integer expirationMonth, final Integer expirationYear,final Integer cVV,final Boolean banned
		,final String postalAdress, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try{
			this.authenticate(username);
			final FormActor formActor = new FormActor();
			formActor.setConfirmed(confirmed);
			formActor.setName(name);
			formActor.setSurname(surname);
			formActor.setEmail(email);
			formActor.setPhoneNumber(phoneNumber);
			formActor.setUsername(usernameString);
			formActor.setPassword(password1);
			formActor.setPassword2(password2);
			formActor.setConfirmed(confirmed);
			CreditCard ejemplo= new CreditCard();
			formActor.setCreditCard(ejemplo);
			formActor.getCreditCard().setAccountNumber(accountNumber);
			formActor.getCreditCard().setBrandName(brandName);
			if(cVV==0){
			formActor.getCreditCard().setcVV(null);
			}else{
				formActor.getCreditCard().setcVV(cVV);
			}
			if(expirationMonth==0){
				formActor.getCreditCard().setExpirationMonth(null);
			}else{
				formActor.getCreditCard().setExpirationMonth(expirationMonth);
			}
			if(expirationYear==0){
				formActor.getCreditCard().setExpirationYear(null);
			}else{
				formActor.getCreditCard().setExpirationYear(expirationYear);
			}
			formActor.getCreditCard().setHolderName(holderName);
			formActor.setPostalAdress(postalAdress);
			Manager manager;
			managerService.flush();
			manager = this.managerService.reconstruct(formActor);
			    Assert.isTrue(this.managerService.checkExceptions(manager));
                final Manager manager2 = this.managerService.saveUserAccount(formActor, manager);
				Manager managerSaved= this.managerService.save(manager2);
				
			final Collection<Manager> managers = this.managerService.allManagers();
			final Manager managerBBDD = this.managerService.findOne(managerSaved.getId());
			Assert.isTrue(managers.contains(managerBBDD));
			final String usernameNow = managerBBDD.getUserAccount().getUsername();
			this.unauthenticate();
			this.authenticate(usernameNow);
			final Manager chorbiLogged = this.managerService.findByPrincipal();
			Assert.isTrue(chorbiLogged.equals(managerBBDD));
			this.unauthenticate();
		}catch(Exception oops){
			caught=oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
}