
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Client;
import domain.Comment;
import domain.Hotel;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository	commentRepository;
	@Autowired
	private ClientService clientService;
	@Autowired
	private HotelService hotelService;


	public CommentService() {
		super();
	}

	public Collection<Comment> cometariosOrdenadosHotel(final int hotelId) {
		Collection<Comment> res;
		res = this.commentRepository.cometariosOrdenadosHotel(hotelId);
		return res;
	}

	public Comment create(Hotel hotel) {
		Comment res;
		Date hoy = new Date(System.currentTimeMillis() - 1000);
		Client client = clientService.findByPrincipal();
		res= new Comment();
		res.setClient(client);
		res.setHotel(hotel);
		res.setCreationDate(hoy);
		return res;
	}

	public Comment save(Comment comment) {
		Assert.notNull(comment);
		Comment res;
		if(comment.getId()==0){
		res=commentRepository.save(comment);
		Client sed=res.getClient();
		Hotel sod=res.getHotel();
		sed.getComments().add(res);
		sod.getComments().add(res);
		clientService.save(sed);
		hotelService.save(sod);
		return res;
		}else{
			res=commentRepository.save(comment);
			return res;
		}
	}

	public void delete(Comment comment) {
		Assert.isTrue(comment.getId()!=0);
		Hotel hotel=comment.getHotel();
		Client client=comment.getClient();
		client.getComments().remove(comment);
		hotel.getComments().remove(comment);
		this.commentRepository.delete(comment);
		clientService.save(client);
		hotelService.save(hotel);
		
	}

	public Comment findOne(int commentId) {
		Comment res;
		res=this.commentRepository.findOne(commentId);
		return res;
	}
}
