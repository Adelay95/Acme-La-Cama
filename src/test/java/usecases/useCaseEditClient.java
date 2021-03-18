
package usecases;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ClientService;
import utilities.AbstractTest;
import domain.Brand;
import domain.Client;
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class useCaseEditClient extends AbstractTest {

	@Autowired
	private ClientService	clientService;

    //Edit the profile of a Client
	//Servicios

	@Test
	public void driver() {
		final Object testingData[][] =  {

			{
				//Este es un caso de prueba normal, para comprobar la funcionalidad general.
				"client1", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","47212867P",
				"Calle Echegaray", null
			},{
				//Este es un caso de prueba intentaremos editar un cliente logeado como manager.
				"manager1", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","47212867P",
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//Este es un caso de prueba intentaremos usar una tarjeta de crédito pasada
				"client1", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2016","406","47212867P",
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//Este es un caso de prueba intentaremos usar una tarjeta de crédito inválida
				"client1", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"holderName",Brand.MASTERCARD,"5555555555554440","12","2020","406","47212867P",
				"Calle Echegaray", ConstraintViolationException.class
			},{
				//Este es un caso de prueba intentaremos introducir campos vacíos.
				"client1", "", "Higueras Galván","frahiggal@us.es","660974252",
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","47212867P",
				"Calle Echegaray", ConstraintViolationException.class
			},{
				//Este es un caso de prueba introduciremos un cVV inválido.
				"client1", "Hey o/", "Higueras Galván","frahiggal@us.es","660974252",
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","56","46852365L",
				"Calle Echegaray", ConstraintViolationException.class
			},{
				//Este es un caso de prueba introduciremos un DNI inválido
				"client1", "Hey o/", "Higueras Galván","frahiggal@us.es","660974252",
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","468523659",
				"Calle Echegaray", ConstraintViolationException.class
			},{
				//En este caso accederemos sin logearnos
				null, "Hey o/", "Higueras Galván","frahiggal@us.es","660974252",
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","46852365S",
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//Este es un caso de prueba introduciremos un email invalido
				"client1", "Hey o/", "Higueras Galván","frahiggal.us.es","660974252",
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","46852365S",
				"Calle Echegaray", ConstraintViolationException.class
			}
			
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], 
				(String) testingData[i][5],(Brand) testingData[i][6],(String) testingData[i][7],new Integer((String)testingData[i][8]),new Integer((String)testingData[i][9]),
				new Integer((String)testingData[i][10]),(String) testingData[i][11],(String) testingData[i][12],(Class<?>) testingData[i][13]);
	}
	
	protected void template(final String username, final String name,final String surname, final String email,final String phoneNumber
		, final String holderName, final Brand brandName , final String accountNumber, final Integer expirationMonth, final Integer expirationYear,
		final Integer cVV,final String DNI,final String postalAdress, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try{
			this.authenticate(username);
			Client client=clientService.findByPrincipal();
			client.setName(name);
			client.setSurname(surname);
			client.setEmail(email);
			client.setPhoneNumber(phoneNumber);
			client.setDniNif(DNI);
			client.setPostalAdress(postalAdress);
			client.getCreditCard().setAccountNumber(accountNumber);
			client.getCreditCard().setBrandName(brandName);
			client.getCreditCard().setcVV(cVV);
			client.getCreditCard().setHolderName(holderName);
			client.getCreditCard().setExpirationMonth(expirationMonth);
			client.getCreditCard().setExpirationYear(expirationYear);
			
			
			Assert.isTrue(this.clientService.checkExceptions(client));
			Client clientSaved=this.clientService.save(client);
			
			
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
