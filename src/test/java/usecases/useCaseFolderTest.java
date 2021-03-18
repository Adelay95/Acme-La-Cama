
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
import utilities.AbstractTest;
import domain.Actor;
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class useCaseFolderTest extends AbstractTest {

	@Autowired
	private FolderService	folderService;
	@Autowired
	private ActorService	actorService;


	//Servicios

	//CREAR CARPETA

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				//Un actor administrador crea una carpeta con un nombre correcto, seria correcto
				"admin", "CarpetaPrueba", null
			}, {
				//un actor no autenticado , por tanto daria error
				null, "CarpetaPrueba", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template(final String username, final String carpeta, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Actor actor = this.actorService.findByPrincipal();
			final Integer numeroCarpetas1 = actor.getFolders().size();
			final Folder folder = this.folderService.create(actor);
			folder.setName(carpeta);
			this.folderService.save(folder);
			Assert.isTrue(numeroCarpetas1 != actor.getFolders().size());
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
				//Un actor elimina una de sus carpetas, seria correcto
				"manager1", 680, null
			}, {
				//Un actor elimina una carpeta de otro usuario, por tanto daria error
				"manager1", 674, null
			}, {
				//un actor no autenticado , por tanto daria error
				null, 680, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template1((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template1(final String username, final int folderId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Actor actor = this.actorService.findByPrincipal();
			final Folder folder = this.folderService.findOne(folderId);
			this.folderService.delete(folder);
			this.folderService.flush();
			Assert.isTrue(!actor.getFolders().contains(folder));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//EDITAR CARPETA

	@Test
	public void driver2() {
		final Object testingData[][] = {
			{
				//Un actor edita una de sus carpetas, seria correcto
				"manager1", 680, null
			}, {
				//Un actor edita una carpeta de otro usuario, por tanto daria error
				"manager1", 674, null
			}, {
				//un actor no autenticado , por tanto daria error
				null, 680, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template2(final String username, final int carpetaId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Actor actor = this.actorService.findByPrincipal();
			final Integer numeroCarpetas1 = actor.getFolders().size();
			final Folder folder = this.folderService.findOne(carpetaId);
			folder.setName("Prueba22");
			this.folderService.save(folder);
			Assert.isTrue(numeroCarpetas1 == actor.getFolders().size());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
