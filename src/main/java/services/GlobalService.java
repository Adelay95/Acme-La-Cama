
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.GlobalRepository;
import domain.Global;

@Service
@Transactional
public class GlobalService {

	@Autowired
	private GlobalRepository	globalRepository;


	public GlobalService() {
		super();
	}

	//No tiene create

	public Global save(final Global global) {
		Assert.notNull(global);
		Global res;
		res = this.globalRepository.save(global);
		return res;
	}

	public void delete(final Global global) {
		Assert.notNull(global);
		Assert.isTrue(global.getId() != 0);
		this.globalRepository.delete(global);
	}

	public Collection<Global> allGlobal() {
		Collection<Global> res;
		res = this.globalRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Global findOne(final int id) {
		Global res;
		res = this.globalRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}
	public Global findTheGlobal() {
		Global res;
		res = this.globalRepository.findTheGlobal();
		Assert.notNull(res);
		return res;
	}

}
