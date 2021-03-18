
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BillRepository;
import domain.Bill;
import domain.BillLine;

@Service
@Transactional
public class BillService {

	@Autowired
	private BillRepository	billRepository;


	public BillService() {
		super();
	}


	public Bill create() {
	     Collection<BillLine> billLines=new HashSet<BillLine>();
	     Bill res= new Bill();
	     res.setBillLines(billLines);
	     res.setTotalAmount(0.0);
	     res.setPaid(false);
	     return res;
	}
	
	public Bill save(Bill bill) {
		Assert.notNull(bill);
		Assert.isTrue(bill.getOffert()!=null || bill.getReservation()!=null);
		Bill res;
		res = this.billRepository.save(bill);
		return res;
		
	}


	public Collection<Bill> allBills() {
		Collection<Bill> res;
		res = this.billRepository.findAll();
		Assert.notNull(res);
		return res;
	}


	public Bill findOne(int i) {
		Bill res;
		res=billRepository.findOne(i);
		return res;
	}


	public Collection<Bill> myBillz(int id) {
		Collection<Bill> allBillz=new HashSet<Bill>();
		allBillz.addAll(billRepository.billzReservations(id));
		allBillz.addAll(billRepository.billzOfferts(id));
		return allBillz;
	}


	

}
