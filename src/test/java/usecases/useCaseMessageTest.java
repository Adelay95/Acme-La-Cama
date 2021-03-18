
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ActorService;
import services.MessageService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class useCaseMessageTest extends AbstractTest {

	@Autowired
	private MessageService	messageService;
	@Autowired
	private ActorService	actorService;


	//Servicios

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				//Un cliente envia un mensaje a otro usuario, seria correcto
				"client1", 631, null
			}, {
				//cliente envia un mensaje así mismo, por tanto daria error
				"client1", 619, IllegalArgumentException.class
			}, {
				//un usuario no logueado envia un mensaje a otro usuario, por tanto daria error
				null, 621, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template(final String username, final int actorId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			this.actorService.findAll();
			final Actor actor = this.actorService.findByPrincipal();
			final Integer actor1 = actor.getMessageSent().size();
			final Actor recipient = this.actorService.findOne(actorId);
			final Integer recipient1 = recipient.getMessageReceived().size();
			final Message mes = this.messageService.create(actor, recipient);
			mes.setBody("pruebilla");
			mes.setSubject("prueba");
			this.actorService.sendMessage(mes);
			this.actorService.flush();
			Assert.notNull(actor1 != actor.getMessageSent().size());
			Assert.notNull(recipient1 != recipient.getMessageReceived().size());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
