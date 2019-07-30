package com.pm.bs.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.bs.beans.Negotiation;
import com.pm.bs.product.repo.NegotiationRepository;
import com.pm.bs.product.repo.UserRepository;
import com.pm.common.entities.PmOrdNgtns;
import com.pm.common.entities.PmUsers;

@Service
public class NegotiationrServiceImpl implements NegotiationrService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderService orderService;
	
	//@Autowired
	private NegotiationRepository orderNegotiationRepo;
	
	@Override
	public Long negotiate(Negotiation prdNgtn) {
		PmOrdNgtns pmOrdngts = new PmOrdNgtns();
		pmOrdngts.setPmOrders(orderService.getOrderById(prdNgtn.getOrderId()));
		pmOrdngts.setNgtnCmpltd(false);
		//pmOrdngts.setNgtnOrdCrncy(new Character(''));
		pmOrdngts.setPmUsersByNgtnByCstrId(userRepository.findById(prdNgtn.getNgtnByCstrId()).orElse(new PmUsers()));
		pmOrdngts.setPmUsersByOrdPlcdCstrId(userRepository.findById(prdNgtn.getOrderPlacedCstrId()).orElse(new PmUsers()));
		pmOrdngts.setNgtnAmnt(prdNgtn.getAmount());
		pmOrdngts = orderNegotiationRepo.save(pmOrdngts);
		return pmOrdngts.getNgtnId();
	}

}
