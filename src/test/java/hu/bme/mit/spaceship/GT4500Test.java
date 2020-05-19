package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  ITorpedoStore primary;
  ITorpedoStore secondary;
  @BeforeEach
  public void init(){
    primary =mock(TorpedoStore.class);
    secondary =mock(TorpedoStore.class);
    this.ship = new GT4500(primary,secondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primary, times(1)).fire(1);
    verify(secondary,times(1)).fire(1);
  }
  @Test
  public void fireTorpedoSingleSecondarySuccess(){
      when(primary.isEmpty()).thenReturn(true);
      when(secondary.fire(1)).thenReturn(true);

      boolean result =ship.fireTorpedo(FiringMode.SINGLE);
      assertEquals(true, result);
      verify(primary, times(1)).isEmpty();
      verify(secondary,times(1)).fire(1);
  }
  @Test
  public void fireTorpedosInSuccession(){
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    boolean shotOne=ship.fireTorpedo(FiringMode.SINGLE);
    boolean shotTwo=ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true, shotOne);
    assertEquals(true, shotTwo);
    verify(primary, times(1)).fire(1);
    verify(secondary,times(1)).fire(1);
  }
  @Test
  public void FireTorpedosInSuccessionSecondaryEmpty(){
    when(primary.fire(1)).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);
    boolean shotOne=ship.fireTorpedo(FiringMode.SINGLE);
    boolean shotTwo=ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true, shotOne);
    assertEquals(true, shotTwo);
    verify(primary, times(2)).fire(1);
    verify(secondary,times(1)).isEmpty();
  }
  @Test
  public void FireTorpedoAllFirstFails(){

    when(primary.fire(1)).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);
    boolean shotOne=ship.fireTorpedo(FiringMode.ALL);
    assertEquals(false, shotOne);
    verify(primary, times(1)).fire(1);
    verify(secondary,times(0)).fire(1);
  }
  @Test
  public void FireTorpedoAllSecondFails(){
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(false);
    boolean shotOne=ship.fireTorpedo(FiringMode.ALL);
    assertEquals(false, shotOne);
    verify(primary, times(1)).fire(1);
    verify(secondary,times(1)).fire(1);
  }
  @Test
  public void FirePrimaryTorpedoFailure(){
    when(primary.fire(1)).thenReturn(false);
    boolean shotOne=ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(false, shotOne);
    verify(primary,times(1)).fire(1);
  }
  @Test
  public void FireModeAllBothEmptySecondNotCalled(){
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);
    boolean shotOne=ship.fireTorpedo(FiringMode.ALL);
    assertEquals(false, shotOne);
    verify(primary, times(1)).isEmpty();
    verify(secondary,times(0)).isEmpty();
  }
  @Test
  public void FirePrimaryThenSecondaryButBothEmpty(){
    when(primary.fire(1)).thenReturn(true);
    boolean first=ship.fireTorpedo(FiringMode.SINGLE);
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);
    boolean second=ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true, first);
    assertEquals(false, second);
    verify(primary, times(2)).isEmpty();
    verify(secondary,times(1)).isEmpty();
    verify(primary,times(1)).fire(1);
  }
  @Test
  public void FirePrimaryFirstBothEMpty(){
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);
    boolean first=ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(false, first);
    verify(primary, times(1)).isEmpty();
    verify(secondary,times(1)).isEmpty();
    verify(primary,times(0)).fire(1);
  }
  @Test
  public void FireModeAllSecondEmpty(){
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(true);
    boolean shotOne=ship.fireTorpedo(FiringMode.ALL);
    assertEquals(false, shotOne);
    verify(primary, times(1)).isEmpty();
    verify(secondary,times(1)).isEmpty();
  }
  @Test
  public void FireModeNull(){
    try{
    boolean shoot=ship.fireTorpedo(null);
    assertEquals(false, shoot);
    }
    catch(NullPointerException e){
      assertEquals(true,true);
    }
  }
  @Test
  public void FireLaser(){
    boolean shoot=ship.fireLaser(FiringMode.ALL);
    assertEquals(false,shoot);
  }
}
