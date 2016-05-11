import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.repository.impl.VehicleRepositoryImpl;
public class VehicleRepositoryTest {
	@Test
	public void getListFromLocationTest(){
		Logger logger = Logger.getLogger("MyLogger");
		VehicleRepositoryImpl vehicleRepositoryImpl = new VehicleRepositoryImpl();
		List<Vehicle> vehicles = vehicleRepositoryImpl.getListFromLocation(1);
		logger.info(vehicles);
		
	}
	

}
