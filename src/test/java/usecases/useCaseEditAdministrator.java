
package usecases;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.AdministratorService;
import utilities.AbstractTest;
import domain.Administrator;
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class useCaseEditAdministrator extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;

    //Edit the profile of a Worker
	//Servicios

	@Test
	public void driver() {
		final Object testingData[][] =  {

			{
				//Este es un caso de prueba normal, para comprobar la funcionalidad general.
				"admin", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"Calle Echegaray", null
			},{
				//Este es un caso de prueba intentaremos editar un cliente logeado como manager.
				"manager1", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"Calle Echegaray", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], 
				(String) testingData[i][5],(Class<?>) testingData[i][6]);
	}
	
	protected void template(final String username, final String name,final String surname, final String email,final String phoneNumber
		, final String postalAdress, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try{
			this.authenticate(username);
			Administrator client=administratorService.findByPrincipal();
			client.setName(name);
			client.setSurname(surname);
			client.setEmail(email);
			client.setPhoneNumber(phoneNumber);
			client.setPostalAdress(postalAdress);
			
			Administrator clientSaved=this.administratorService.save(client);
			final Collection<Administrator> chorbies = this.administratorService.allAdministrator();
			final Administrator chorbiBBDD = this.administratorService.findOne(clientSaved.getId());
			Assert.isTrue(chorbies.contains(chorbiBBDD));
			final String usernameNow = chorbiBBDD.getUserAccount().getUsername();
			this.unauthenticate();
			this.authenticate(usernameNow);
			final Administrator chorbiLogged = this.administratorService.findByPrincipal();
			Assert.isTrue(chorbiLogged.equals(chorbiBBDD));
			this.unauthenticate();
		}catch(Exception oops){
			caught=oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
}
