package com.ksgagro.gps.service;

import java.util.*;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.Vehicle;

@Service
public class RoleFilter {
	private Map<String, List<Integer>> accessMap;
	
	@Autowired
	VehicleService vehicleService;
	@PostConstruct
	public void init(){
		accessMap = new HashMap<>();
		List<Integer> autorizedVehicleIds = new ArrayList<>();
		for(Vehicle vehicle: vehicleService.getList()){
			autorizedVehicleIds.add(vehicle.getId());
		}
		accessMap = new HashMap<>();
		List<Integer> rentIds = new ArrayList<Integer>();
		rentIds.add(124);
		rentIds.add(125);
		rentIds.add(126);
		rentIds.add(66);
		rentIds.add(111);
		rentIds.add(130);
		rentIds.add(131);
		rentIds.add(132);
		rentIds.add(133);
		rentIds.add(134);
		rentIds.add(135);
		rentIds.add(136);
		rentIds.add(137);
		rentIds.add(122);
		
		accessMap.put("ROLE_RENT", rentIds);
		accessMap.put("ROLE_ADMIN", autorizedVehicleIds);	
		accessMap.put("ROLE_AGRO_USER", autorizedVehicleIds);
	}
	 public Map<String, List<Integer>> getAccssMap(){
		 return this.accessMap;
	 }
	 
	 public List<Integer> getVehiclesList(Collection<? extends GrantedAuthority> roles){
		 List<Integer> list = new ArrayList<>();
		 for(GrantedAuthority role: roles){
			 list.addAll(accessMap.get(role.toString()));
		 }
		 return list;
	 }

}
