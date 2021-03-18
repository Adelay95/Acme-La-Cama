
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.AdministratorService;
import services.ManagerService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Manager;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class useDashBoard extends AbstractTest {

	//Servicios

	@Autowired
	private AdministratorService	adminService;
	@Autowired
	private ManagerService			managerService;


	//Visualizar dashboard como manager
	

	@Test
	public void driver() {
		final Object testingData[][] = {

			{
				//Visualizar dashboard como manager
				"manager2", 216, null
			}, {
				//Visualizar dashboard como admin
				"admin", 216, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template(final String username, final int hotel, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {

			this.authenticate(username);
			final Manager m = this.managerService.findByPrincipal();
			Assert.isTrue(m instanceof Manager);
			final Double ocupacionHotelera = this.adminService.ocupacionHotelera(hotel);
			final Double mediaAsistenciaExcur = this.adminService.mediaAsistenciaExcur(hotel);
			final Double mediaStarsComentarios = this.adminService.mediaStarsComentarios(hotel);
			final Long numClientesReservas = this.adminService.numClientesReservas(hotel);
			final Double mediaGastado = this.adminService.mediaGastado(hotel);

			Assert.isTrue(ocupacionHotelera == 0.4);

			Assert.isTrue(mediaAsistenciaExcur == 0.0);

			Assert.isTrue(mediaStarsComentarios == 1.0);

			Assert.isTrue(numClientesReservas == 2);

			Assert.isTrue(mediaGastado == 1000.0);

			this.unauthenticate();
		} catch (final Exception oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	//Visualizar dashboard como admin
	@Test
	public void driver2() {
		final Object testingData[][] = {

			{
				//Visualizar dashboard como admin
				"admin", null
			}, {
				//Visualizar dashboard como manager
				"manager2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void template2(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {

			this.authenticate(username);
			final Administrator a = this.adminService.findByPrincipal();
			Assert.isTrue(a instanceof Administrator);
			final Integer min = this.adminService.min();
			final Double avg = this.adminService.avg();
			final Integer max = this.adminService.max();
			final Double avgWorker = this.adminService.avgWorker();
			final Integer minWorker = this.adminService.minWorker();
			final Integer maxWorker = this.adminService.maxWorker();

			Assert.isTrue(min == 2);

			Assert.isTrue(avg == 2.25);

			Assert.isTrue(max == 3);

			Assert.isTrue(avgWorker == 1.75);

			Assert.isTrue(minWorker == 0);

			Assert.isTrue(maxWorker == 4);

			this.unauthenticate();
		} catch (final Exception oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
