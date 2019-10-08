package de.gedoplan.seminar.mocking.personscoring.service;

import de.gedoplan.seminar.mocking.personscoring.entity.Familienstand;
import de.gedoplan.seminar.mocking.personscoring.entity.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
@RunWith(PowerMockRunner.class)
@PrepareForTest(PersonScoringService.class)
public class PersonScoringServiceTest2 {

    private PersonScoringService service;

    @Before
    public void setUp() throws Exception {
        this.service = PowerMockito.spy(new PersonScoringService());
    }

    @Test
    public void berechnenScore() throws Exception {

        Person person = new Person("max", "mustermann",
                Familienstand.LEDIG, LocalDate.of(1980, 8, 24));
        PowerMockito.when(this.service ,"pruefenWohngegend", person).thenReturn(4);
        Integer actual = service.berechnenScore(person);


        Integer expected = 6;
        assertEquals(expected, actual );
    }
}