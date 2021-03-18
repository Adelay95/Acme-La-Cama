
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import domain.Actor;
import domain.Administrator;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	@Autowired
	private BannerRepository	bannerRepository;
	@Autowired
	private ActorService		actorService;


	public BannerService() {
		super();
	}

	public Banner create() {
		Banner res;
		res = new Banner();
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);
		return res;
	}

	public Banner save(final Banner banner) {
		Assert.notNull(banner);
		Banner res;
		res = this.bannerRepository.save(banner);
		return res;
	}

	public void delete(final Banner banner) {
		Assert.notNull(banner);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);
		Assert.isTrue(banner.getId() != 0);

		this.bannerRepository.delete(banner);
	}

	public Collection<Banner> allBanners() {
		Collection<Banner> res;
		res = this.bannerRepository.findAll();
		Assert.notNull(res);
		return res;
	}
	public Banner findOne(final int id) {
		Banner res;
		res = this.bannerRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public String bannerWelcome() {

		Banner baner = null;
		String res = null;
		final Random banner = new Random();
		final List<Banner> banners = new ArrayList<Banner>(this.allBanners());
		final int y = banner.nextInt(banners.size());
		baner = banners.get(y);
		res = baner.getBannerUrl();

		return res;
	}

}
