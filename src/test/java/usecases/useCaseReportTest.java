
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.FolderService;
import services.HotelService;
import services.ServicesService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class useCaseReportTest extends AbstractTest {

	@Autowired
	private ServicesService	servicesService;
	@Autowired
	private HotelService	hotelService;
	@Autowired
	private FolderService	folderService;


	//Servicios

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				//Un cliente reporta un hotel, seria correcto
				"client1", 11, null
			}, {
				//Nadie reporta a un hotel, por tanto daria error
				"manager1", 11, IllegalArgumentException.class
			}, {
				//Nadie reporta a un hotel, por tanto daria error
				null, 11, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template(final String username, final int hotelId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Administrator admin = this.servicesService.getAdmin();
			final Folder folder = this.folderService.findREPORT(admin);
			final Integer carpeta = folder.getMessages().size();
			final Integer mensaje = admin.getMessageReceived().size();
			this.hotelService.reportHotel(hotelId);

			Assert.isTrue(carpeta != folder.getMessages().size());
			Assert.isTrue(mensaje != admin.getMessageReceived().size());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
