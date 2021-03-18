
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BillLineRepository;
import domain.Bill;
import domain.BillLine;

@Service
@Transactional
public class BillLineService {

	@Autowired
	private BillLineRepository	billLineRepository;
	@Autowired
	private BillService	billService;


	public BillLineService() {
		super();
	}


	public BillLine save(BillLine billLined) {
		Assert.notNull(billLined);
		BillLine res;
		res = this.billLineRepository.save(billLined);
		return res;
		
	}


	public BillLine create(Bill saved) {
		BillLine res;
		res=new BillLine();
		res.setBill(saved);
		return res;
	}


	public BillLine findOne(int billLineId) {
		BillLine res;
		res=billLineRepository.findOne(billLineId);
		return res;
	}


	public void delete(BillLine billLine) {
	      Assert.notNull(billLine);
	      Bill bill=billLine.getBill();
	      Double totalAmount=bill.getTotalAmount();
	      bill.setTotalAmount(totalAmount-billLine.getAmount());
	      bill.getBillLines().remove(billLine);
	      billLineRepository.delete(billLine);
	      billService.save(bill);
	}


	public void actualizeAmount(BillLine billLine) {
		Bill bill = billLine.getBill();
		Double totalAmount=bill.getTotalAmount();
		if(billLine.getId()==0){
		
		bill.setTotalAmount(totalAmount+billLine.getAmount());
		BillLine saved=this.save(billLine);
		bill.getBillLines().add(saved);
		this.billService.save(bill);
	    }else{
	    	BillLine before=findOne(billLine.getId());
	    	bill.setTotalAmount(totalAmount-before.getAmount()+billLine.getAmount());
	    	this.save(billLine);
	    	this.billService.save(bill);
	    }
	}

}
