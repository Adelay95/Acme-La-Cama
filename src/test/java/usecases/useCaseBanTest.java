
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ActorService;
import services.AdministratorService;
import services.ManagerService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class useCaseBanTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ManagerService			managerService;
	@Autowired
	private ActorService			actorService;


	//Servicios

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				//El admin banea a un Manager, seria correcto
				"admin", 10, null
			}, {
				//Nadie banea a un Manager, por tanto daria error
				null, 10, IllegalArgumentException.class
			}, {
				//Un manager banear a otro manager, por tanto daria error
				"manager1", 215, IllegalArgumentException.class
			}, {
				//El admin banear a un manager ya baneado y da error.
				"admin", 677, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template(final String username, final int managerId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Actor actor = this.actorService.findByPrincipal();
			final Integer enviado = actor.getMessageSent().size();
			final Manager c = this.managerService.findOne(managerId);
			final Integer recibido = c.getMessageReceived().size();
			this.administratorService.banManager(managerId);
			Assert.isTrue(c.getBanned() == true);
			Assert.isTrue(enviado != actor.getMessageSent().size());
			Assert.isTrue(recibido != c.getMessageReceived().size());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
