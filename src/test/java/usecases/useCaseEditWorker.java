
package usecases;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.WorkerService;
import utilities.AbstractTest;
import domain.Brand;
import domain.Worker;
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class useCaseEditWorker extends AbstractTest {

	@Autowired
	private WorkerService	workerService;

    //Edit the profile of a Worker
	//Servicios

	@Test
	public void driver() {
		final Object testingData[][] =  {

			{
				//Este es un caso de prueba normal, para comprobar la funcionalidad general.
				"worker1", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","123.23",
				"Calle Echegaray", null
			},{
				//Este es un caso de prueba intentaremos editar un cliente logeado como manager.
				"manager1", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","123.23",
				"Calle Echegaray", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], 
				(String) testingData[i][5],(Brand) testingData[i][6],(String) testingData[i][7],new Integer((String)testingData[i][8]),new Integer((String)testingData[i][9]),
				new Integer((String)testingData[i][10]),new Double((String)testingData[i][11]),(String) testingData[i][12],(Class<?>) testingData[i][13]);
	}
	
	protected void template(final String username, final String name,final String surname, final String email,final String phoneNumber
		, final String holderName, final Brand brandName , final String accountNumber, final Integer expirationMonth, final Integer expirationYear,
		final Integer cVV,final Double salario, final String postalAdress, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try{
			this.authenticate(username);
			Worker client=workerService.findByPrincipal();
			client.setName(name);
			client.setSurname(surname);
			client.setEmail(email);
			client.setPhoneNumber(phoneNumber);
			client.setPostalAdress(postalAdress);
			client.getCreditCard().setAccountNumber(accountNumber);
			client.getCreditCard().setBrandName(brandName);
			client.getCreditCard().setcVV(cVV);
			client.getCreditCard().setHolderName(holderName);
			client.getCreditCard().setExpirationMonth(expirationMonth);
			client.getCreditCard().setExpirationYear(expirationYear);
			client.setSalary(salario);
			
			Assert.isTrue(this.workerService.checkExceptions(client));
			Worker clientSaved=this.workerService.save(client);
			
			
			final Collection<Worker> chorbies = this.workerService.allWorkers();
			final Worker chorbiBBDD = this.workerService.findOne(clientSaved.getId());
			Assert.isTrue(chorbies.contains(chorbiBBDD));
			final String usernameNow = chorbiBBDD.getUserAccount().getUsername();
			this.unauthenticate();
			this.authenticate(usernameNow);
			final Worker chorbiLogged = this.workerService.findByPrincipal();
			Assert.isTrue(chorbiLogged.equals(chorbiBBDD));
			this.unauthenticate();
		}catch(Exception oops){
			caught=oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
}
