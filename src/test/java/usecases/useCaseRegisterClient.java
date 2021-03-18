
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

import services.ClientService;
import utilities.AbstractTest;
import domain.Brand;
import domain.Client;
import domain.CreditCard;
import forms.FormActor;
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class useCaseRegisterClient extends AbstractTest {

	@Autowired
	private ClientService	clientService;

    //Register to the system as a client.
	//Servicios

	@Test
	public void driver() {
		final Object testingData[][] =  {

			{
				//Este es un caso de prueba normal, para comprobar la funcionalidad general
				null, "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernamePrueba", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","47212867P",
				"Calle Echegaray", null
			},{
				//En este caso probamos con una tarjeta de crédito pasada, debería dar error
				null, "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"username2", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2016","406","47212868P",
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//En este caso probamos con contraseñas que no coinciden
				null, "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"username3", "contraseña", "contraseña2", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","47212870P",
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//En este caso probamos con un numero de tarjeta invalido
				null, "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernameAgore3", "contraseña", "contraseña", true,
				"holderName",Brand.AMEX,"5555555555554440","12","2020","406","47212872P",
				"Calle Echegaray", ConstraintViolationException.class
			}
			,{
				//En este caso probamos con nombre de usuario que ya está en uso, debería dar error
				null, "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernamePrueba", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","47212869P",
				"Calle Echegaray", DataIntegrityViolationException.class
			}
			
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], 
				(String)testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],(Boolean) testingData[i][8],(String) testingData[i][9],
				(Brand) testingData[i][10],(String) testingData[i][11],new Integer((String)testingData[i][12]),new Integer((String)testingData[i][13]),
				new Integer((String)testingData[i][14]),(String) testingData[i][15],(String) testingData[i][16],(Class<?>) testingData[i][17]);
	}
	
	protected void template(final String username, final String name,final String surname, final String email,final String phoneNumber,final String usernameString
		, final String password1, final String password2, final Boolean confirmed, final String holderName, final Brand brandName
		, final String accountNumber, final Integer expirationMonth, final Integer expirationYear,final Integer cVV,final String DNI
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
			formActor.setDniNif(DNI);
			formActor.setPostalAdress(postalAdress);
			Client client;
            client = this.clientService.reconstruct(formActor);
            
			    Assert.isTrue(this.clientService.checkDNI(client.getDniNif()));
			    Assert.isTrue(this.clientService.checkExceptions(client));
                final Client client2 = this.clientService.saveUserAccount(formActor, client);
				Client clientSaved= this.clientService.save(client2);
				
			final Collection<Client> chorbies = this.clientService.allClients();
			final Client chorbiBBDD = this.clientService.findOne(clientSaved.getId());
			Assert.isTrue(chorbies.contains(chorbiBBDD));
			final String usernameNow = chorbiBBDD.getUserAccount().getUsername();
			this.unauthenticate();
			this.authenticate(usernameNow);
			final Client chorbiLogged = this.clientService.findByPrincipal();
			Assert.isTrue(chorbiLogged.equals(chorbiBBDD));
			this.unauthenticate();
		}catch(Exception oops){
			caught=oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
}
