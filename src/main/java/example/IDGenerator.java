package example;

import java.util.UUID;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class IDGenerator {

	public static void main(String[] args) {
		UUID uuid = Generators.randomBasedGenerator().generate();
		System.out.println(uuid);
		uuid = Generators.timeBasedGenerator().generate();
		System.out.println(uuid);

		// MAC Address
		EthernetAddress addr = EthernetAddress.fromInterface();
		System.out.println(addr);

		TimeBasedGenerator gen = Generators.timeBasedGenerator(addr);

		for (int i = 0; i < 100; i++) {
			uuid = gen.generate();
			System.out.println(uuid);
		}
	}
}
