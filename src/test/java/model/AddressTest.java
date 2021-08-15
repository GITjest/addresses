package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    public void shouldConvertJsonToCorrectAddress() throws JsonProcessingException {
        String json = "{\n" +
                "    \"house_number\": \"20A\",\n" +
                "    \"road\": \"Szyszkowa\",\n" +
                "    \"neighbourhood\": \"Załuski\",\n" +
                "    \"suburb\": \"Włochy\",\n" +
                "    \"city\": \"Warszawa\",\n" +
                "    \"county\": \"Warszawa\",\n" +
                "    \"state\": \"województwo mazowieckie\",\n" +
                "    \"postcode\": \"02-285\",\n" +
                "    \"country\": \"Polska\",\n" +
                "    \"country_code\": \"pl\"\n" +
                "  }";

        ObjectMapper objectMapper = new ObjectMapper();
        Address address = objectMapper.readValue(json, Address.class);

        assertAll(
                () -> assertEquals(address.getCity(), "Warszawa"),
                () -> assertEquals(address.getCountry(), "Polska"),
                () -> assertEquals(address.getHouseNumber(), "20A"),
                () -> assertEquals(address.getRoad(), "Szyszkowa")
        );
    }
}