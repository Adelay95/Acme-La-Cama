
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ActorService;
import services.FolderService;
import services.ServicesService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Services;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class useCaseServicesTest extends AbstractTest {

	@Autowired
	private FolderService	folderService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private ServicesService	servicesService;


	//Servicios

	//CREAR Servicio

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				//Un actor administrador crea una servicio, seria correcto
				"admin", null
			}, {
				//un actor que no es administrador crea un servicio , por tanto daria error
				"manager1", IllegalArgumentException.class
			}, {
				//un actor no autenticado intenta crear un servicio , por tanto daria error
				null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void template(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Integer numero = this.servicesService.allServices().size();
			final Services serv = this.servicesService.create();
			serv.setName("Prueba123");
			serv.setImageURL("http://www.google.es");
			this.servicesService.save(serv);
			this.servicesService.flush();
			Assert.isTrue(numero != this.servicesService.allServices().size());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//ELIMINAR

	@Test
	public void driver1() {
		final Object testingData[][] = {
			{
				//Un actor elimina un servicio, seria correcto
				"admin", 570, null
			}, {
				//Un actor que no es administrador elimina un servicio, por tanto daria error
				"manager1", 570, IllegalArgumentException.class
			}, {
				//un actor elimina un servicio , por tanto daria error
				null, 570, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template1((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template1(final String username, final int servicesId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Integer numero = this.servicesService.allServices().size();
			final Services serv = this.servicesService.findOne(servicesId);
			this.servicesService.delete(serv);
			Assert.isTrue(numero != this.servicesService.allServices().size());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//EDITAR Servicio

	@Test
	public void driver2() {
		final Object testingData[][] = {
			{
				//Un actor edita una de sus carpetas, seria correcto
				"admin", 570, null
			}, {
				//Un actor edita una carpeta de otro usuario, por tanto daria error
				"manager1", 570, IllegalArgumentException.class
			}, {
				//un actor no autenticado , por tanto daria error
				null, 570, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template2(final String username, final int servicesId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Actor actor = this.actorService.findByPrincipal();
			final Integer numero = this.servicesService.allServices().size();
			final Services serv = this.servicesService.findOne(servicesId);
			serv.setName("Prueba22");
			serv.setImageURL("http://www.google.es");
			this.servicesService.save(serv);
			Assert.isTrue(numero == this.servicesService.allServices().size());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
