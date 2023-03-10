package io.camunda.example.songrequest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ZeebeServiceTest {

  @Test
  public void sanityCheck() {
    assertTrue(true);
  }

  @Test
  public void createInstance() {
    ZeebeService zeebeService = new ZeebeService();
    assertNotNull(zeebeService);
    Long result = zeebeService.createInstance(new HashMap<>());
    assert (result > 0);
  }
}
