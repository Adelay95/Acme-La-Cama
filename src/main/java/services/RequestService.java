
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Manager;
import domain.Request;
import domain.State;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;


	public RequestService() {
		super();
	}

	public Request create(final Manager manager) {
		Request res;
		res = new Request();
		res.setManager(manager);
		res.setState(State.PENDING);
		return res;
	}
	public Request save(final Request request) {
		Assert.notNull(request);
		Request res;
		res = this.requestRepository.save(request);
		return res;
	}

	public void delete(final Request request) {
		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);
		this.requestRepository.delete(request);
	}

	public Collection<Request> allRequest() {
		Collection<Request> res;
		res = this.requestRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Request findOne(final int id) {
		Request res;
		res = this.requestRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}
}
