
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

import services.HotelService;
import services.ManagerService;
import services.WorkerService;
import utilities.AbstractTest;
import domain.Brand;
import domain.CreditCard;
import domain.Hotel;
import domain.Worker;
import forms.FormActor;
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class useCaseRegisterWorker extends AbstractTest {

	@Autowired
	private WorkerService	workerService;
	@Autowired
	private HotelService	hotelService;
	@Autowired
	private ManagerService managerService;
	

    //Register to the system as a worker.
	//Servicios

	@Test
	public void driver() {
		final Object testingData[][] =  {

			{
				//Este es un caso de prueba normal, para comprobar la funcionalidad general.
				"manager2", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernamePrueba2", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","123.23","216",
				"Calle Echegaray", null
			},{
				//Este es un caso de prueba donde el manager no es el propietario del hotel, saltaría error.
				"manager1", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernamePrueba56", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","123.23","216",
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//Este es un caso de prueba donde el hotel no está aceptado o ya ha pasado la fecha de su propuesta.
				"manager1", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernamePrueba562", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","123.23","11",
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//Este es un caso de prueba donde no hay nadie registrado e intenta registrar un trabajador.
				null, "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernamePrueba5621", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","123.23","216",
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//Probemos ahora con el máximo valor de double en el salario.
				"manager2", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernamePrueba56221", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406",""+Double.MAX_VALUE,"216",
				"Calle Echegaray", null
			},{
				//Probemos ahora con un id de hotel inválido.
				"manager2", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernamePrueba5622421", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","123.12","215",
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//Probemos ahora con un usuario registrado que no sea manager.
				"admin", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernamePrueba5623221", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","123.23","216",
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//Probemos ahora con contraseñas que no coinciden.
				"manager2", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernamePrueba5625221", "contraseña", "contraseña1", true,
				"holderName",Brand.MASTERCARD,"5555555555554444","12","2020","406","123.23","216",
				"Calle Echegaray", IllegalArgumentException.class
			},{
				//Probemos ahora con una tarjeta de crédito inválida.
				"manager2", "Francisco Javier", "Higueras Galván","frahiggal@us.es","660974252",
				"usernamePrueba5622821", "contraseña", "contraseña", true,
				"holderName",Brand.MASTERCARD,"5555555555554440","12","2020","406","123.23","216",
				"Calle Echegaray", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], 
				(String)testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],(Boolean) testingData[i][8],(String) testingData[i][9],
				(Brand) testingData[i][10],(String) testingData[i][11],new Integer((String)testingData[i][12]),new Integer((String)testingData[i][13]),
				new Integer((String)testingData[i][14]),new Double((String) testingData[i][15]),new Integer((String) testingData[i][16]),(String) testingData[i][17],(Class<?>) testingData[i][18]);
	}
	
	protected void template(final String username, final String name,final String surname, final String email,final String phoneNumber,final String usernameString
		, final String password1, final String password2, final Boolean confirmed, final String holderName, final Brand brandName
		, final String accountNumber, final Integer expirationMonth, final Integer expirationYear,final Integer cVV, final Double salario, final Integer hotelId
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
			Worker worker;
			Hotel hotel=hotelService.findOne(hotelId);
			formActor.setHotel(hotel);
			formActor.setSalary(salario);
			
				worker = this.workerService.reconstruct(formActor);
				Assert.isTrue(formActor.getHotel().getRequest().getManager().getId() == this.managerService.findByPrincipal().getId());
				Assert.isTrue(this.workerService.checkGoodHotel(formActor.getHotel()));
				Assert.isTrue(this.workerService.checkExceptions(worker));
				final Worker worker2 = this.workerService.saveUserAccount(formActor, worker);
				final Worker workerSaved=this.workerService.save(worker2);

				
			final Collection<Worker> workers = this.workerService.allWorkers();
			final Worker workerBBDD = this.workerService.findOne(workerSaved.getId());
			Assert.isTrue(workers.contains(workerBBDD));
			final String usernameNow = workerBBDD.getUserAccount().getUsername();
			this.unauthenticate();
			this.authenticate(usernameNow);
			final Worker workerLogged = this.workerService.findByPrincipal();
			Assert.isTrue(workerLogged.equals(workerBBDD));
			this.unauthenticate();
		}catch(Exception oops){
			caught=oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
}