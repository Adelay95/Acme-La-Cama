
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.BillLineService;
import services.BillService;
import services.WorkerService;
import utilities.AbstractTest;
import domain.Bill;
import domain.BillLine;
import domain.Worker;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class useCaseBillLine extends AbstractTest {

	//Servicios
	@Autowired
	private BillLineService	billLineService;
	@Autowired
	private BillService		billService;

	@Autowired
	private WorkerService	workerService;


	//Crear billline como worker

	@Test
	public void driver() {
		final Object testingData[][] = {

			{
				//Creo una bill line correctamente como worker
				"worker5", 669, null
			}, {
				//No creo bill lien como manager
				"manager1", 669, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template(final String username, final int billId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {

			this.authenticate(username);
			final Worker w = this.workerService.findByPrincipal();

			Assert.isTrue(w instanceof Worker);
			final Bill bill = this.billService.findOne(billId);
			final BillLine line = this.billLineService.create(bill);
			line.setAmount(32.2);
			line.setReason("rason");
			final BillLine save = this.billLineService.save(line);
			bill.getBillLines().add(save);
			final Bill bill2 = this.billService.save(bill);
			Assert.isTrue(bill2.getBillLines().contains(save));

			this.unauthenticate();
		} catch (final Exception oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
