package sn.architech.etatcivil.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import sn.architech.etatcivil.domain.enumeration.Sexe;
import sn.architech.etatcivil.web.rest.TestUtil;

class PersonneTest {

    @Test
    public void testCentre() {
        Personne personne = new Personne();
        Centre centre = new Centre();
        Personne actualCentreResult = personne.centre(centre);
        assertSame(personne, actualCentreResult);
        assertSame(centre, actualCentreResult.getCentre());
    }

    @Test
    public void testEquals() {
        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName("Department Name");
        department.setDepartmentCode(1);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(1);

        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName("Commune Name");
        commune.communeCode(1);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName("Commune Name");
        commune.setCommuneCode(1);
        commune.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement = new Arrondissement();
        arrondissement.setArrondissementCode(1);
        arrondissement.arrondissementCode(1);
        arrondissement.arrondissementName("Arrondissement Name");
        arrondissement.setCommune(commune);
        arrondissement.setCentres(new HashSet<Centre>());
        arrondissement.setId(123L);
        arrondissement.id(123L);
        arrondissement.setArrondissementName("Arrondissement Name");

        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief("Centre Chief");
        centre.setArrondissement(arrondissement);
        centre.setCentreChief("Centre Chief");
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName("Centre Name");
        centre.setCentreName("Centre Name");

        Personne personne = new Personne();
        personne.setLastName("Doe");
        personne.id(123L);
        personne.setFatherName("Father Name");
        personne.sexe(Sexe.MASCULIN);
        personne.setNumberRegister(1L);
        personne.firstName("Jane");
        personne.setMotherLastName("Doe");
        personne.setFirstName("Jane");
        personne.motherFirstName("Jane");
        personne.setSexe(Sexe.MASCULIN);
        personne.setCentre(centre);
        personne.dateOfBirthday(null);
        personne.numberRegister(1L);
        personne.setId(123L);
        personne.motherLastName("Doe");
        personne.setHourOfBithday(null);
        personne.fatherName("Father Name");
        personne.setDateOfBirthday(null);
        personne.setMotherFirstName("Jane");
        personne.lastName("Doe");
        personne.hourOfBithday(null);
        assertFalse(personne.equals(null));
    }

    @Test
    public void testEquals2() {
        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName("Department Name");
        department.setDepartmentCode(1);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(1);

        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName("Commune Name");
        commune.communeCode(1);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName("Commune Name");
        commune.setCommuneCode(1);
        commune.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement = new Arrondissement();
        arrondissement.setArrondissementCode(1);
        arrondissement.arrondissementCode(1);
        arrondissement.arrondissementName("Arrondissement Name");
        arrondissement.setCommune(commune);
        arrondissement.setCentres(new HashSet<Centre>());
        arrondissement.setId(123L);
        arrondissement.id(123L);
        arrondissement.setArrondissementName("Arrondissement Name");

        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief("Centre Chief");
        centre.setArrondissement(arrondissement);
        centre.setCentreChief("Centre Chief");
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName("Centre Name");
        centre.setCentreName("Centre Name");

        Personne personne = new Personne();
        personne.setLastName("Doe");
        personne.id(123L);
        personne.setFatherName("Father Name");
        personne.sexe(Sexe.MASCULIN);
        personne.setNumberRegister(1L);
        personne.firstName("Jane");
        personne.setMotherLastName("Doe");
        personne.setFirstName("Jane");
        personne.motherFirstName("Jane");
        personne.setSexe(Sexe.MASCULIN);
        personne.setCentre(centre);
        personne.dateOfBirthday(null);
        personne.numberRegister(1L);
        personne.setId(123L);
        personne.motherLastName("Doe");
        personne.setHourOfBithday(null);
        personne.fatherName("Father Name");
        personne.setDateOfBirthday(null);
        personne.setMotherFirstName("Jane");
        personne.lastName("Doe");
        personne.hourOfBithday(null);
        assertFalse(personne.equals("Different type to Personne"));
    }

    @Test
    public void testEquals3() {
        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName("Department Name");
        department.setDepartmentCode(1);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(1);

        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName("Commune Name");
        commune.communeCode(1);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName("Commune Name");
        commune.setCommuneCode(1);
        commune.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement = new Arrondissement();
        arrondissement.setArrondissementCode(1);
        arrondissement.arrondissementCode(1);
        arrondissement.arrondissementName("Arrondissement Name");
        arrondissement.setCommune(commune);
        arrondissement.setCentres(new HashSet<Centre>());
        arrondissement.setId(123L);
        arrondissement.id(123L);
        arrondissement.setArrondissementName("Arrondissement Name");

        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief("Centre Chief");
        centre.setArrondissement(arrondissement);
        centre.setCentreChief("Centre Chief");
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName("Centre Name");
        centre.setCentreName("Centre Name");

        Personne personne = new Personne();
        personne.setLastName("Doe");
        personne.id(123L);
        personne.setFatherName("Father Name");
        personne.sexe(Sexe.MASCULIN);
        personne.setNumberRegister(1L);
        personne.firstName("Jane");
        personne.setMotherLastName("Doe");
        personne.setFirstName("Jane");
        personne.motherFirstName("Jane");
        personne.setSexe(Sexe.MASCULIN);
        personne.setCentre(centre);
        personne.dateOfBirthday(null);
        personne.numberRegister(1L);
        personne.setId(123L);
        personne.motherLastName("Doe");
        personne.setHourOfBithday(null);
        personne.fatherName("Father Name");
        personne.setDateOfBirthday(null);
        personne.setMotherFirstName("Jane");
        personne.lastName("Doe");
        personne.hourOfBithday(null);
        assertTrue(personne.equals(personne));
        int expectedHashCodeResult = personne.hashCode();
        assertEquals(expectedHashCodeResult, personne.hashCode());
    }

    @Test
    public void testEquals4() {
        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName("Department Name");
        department.setDepartmentCode(1);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(1);

        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName("Commune Name");
        commune.communeCode(1);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName("Commune Name");
        commune.setCommuneCode(1);
        commune.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement = new Arrondissement();
        arrondissement.setArrondissementCode(1);
        arrondissement.arrondissementCode(1);
        arrondissement.arrondissementName("Arrondissement Name");
        arrondissement.setCommune(commune);
        arrondissement.setCentres(new HashSet<Centre>());
        arrondissement.setId(123L);
        arrondissement.id(123L);
        arrondissement.setArrondissementName("Arrondissement Name");

        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief("Centre Chief");
        centre.setArrondissement(arrondissement);
        centre.setCentreChief("Centre Chief");
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName("Centre Name");
        centre.setCentreName("Centre Name");

        Personne personne = new Personne();
        personne.setLastName("Doe");
        personne.id(123L);
        personne.setFatherName("Father Name");
        personne.sexe(Sexe.MASCULIN);
        personne.setNumberRegister(1L);
        personne.firstName("Jane");
        personne.setMotherLastName("Doe");
        personne.setFirstName("Jane");
        personne.motherFirstName("Jane");
        personne.setSexe(Sexe.MASCULIN);
        personne.setCentre(centre);
        personne.dateOfBirthday(null);
        personne.numberRegister(1L);
        personne.setId(123L);
        personne.motherLastName("Doe");
        personne.setHourOfBithday(null);
        personne.fatherName("Father Name");
        personne.setDateOfBirthday(null);
        personne.setMotherFirstName("Jane");
        personne.lastName("Doe");
        personne.hourOfBithday(null);

        Department department1 = new Department();
        department1.departmentName("Department Name");
        department1.setRegion(new Region());
        department1.setId(123L);
        department1.setDepartmentName("Department Name");
        department1.setDepartmentCode(1);
        department1.setCommunes(new HashSet<Commune>());
        department1.id(123L);
        department1.departmentCode(1);

        Commune commune1 = new Commune();
        commune1.id(123L);
        commune1.communeName("Commune Name");
        commune1.communeCode(1);
        commune1.setId(123L);
        commune1.setDepartment(department1);
        commune1.setCommuneName("Commune Name");
        commune1.setCommuneCode(1);
        commune1.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement1 = new Arrondissement();
        arrondissement1.setArrondissementCode(1);
        arrondissement1.arrondissementCode(1);
        arrondissement1.arrondissementName("Arrondissement Name");
        arrondissement1.setCommune(commune1);
        arrondissement1.setCentres(new HashSet<Centre>());
        arrondissement1.setId(123L);
        arrondissement1.id(123L);
        arrondissement1.setArrondissementName("Arrondissement Name");

        Centre centre1 = new Centre();
        centre1.id(123L);
        centre1.centreChief("Centre Chief");
        centre1.setArrondissement(arrondissement1);
        centre1.setCentreChief("Centre Chief");
        centre1.setId(123L);
        centre1.setPersonnes(new HashSet<Personne>());
        centre1.centreName("Centre Name");
        centre1.setCentreName("Centre Name");

        Personne personne1 = new Personne();
        personne1.setLastName("Doe");
        personne1.id(123L);
        personne1.setFatherName("Father Name");
        personne1.sexe(Sexe.MASCULIN);
        personne1.setNumberRegister(1L);
        personne1.firstName("Jane");
        personne1.setMotherLastName("Doe");
        personne1.setFirstName("Jane");
        personne1.motherFirstName("Jane");
        personne1.setSexe(Sexe.MASCULIN);
        personne1.setCentre(centre1);
        personne1.dateOfBirthday(null);
        personne1.numberRegister(1L);
        personne1.setId(123L);
        personne1.motherLastName("Doe");
        personne1.setHourOfBithday(null);
        personne1.fatherName("Father Name");
        personne1.setDateOfBirthday(null);
        personne1.setMotherFirstName("Jane");
        personne1.lastName("Doe");
        personne1.hourOfBithday(null);
        assertTrue(personne.equals(personne1));
        int expectedHashCodeResult = personne.hashCode();
        assertEquals(expectedHashCodeResult, personne1.hashCode());
    }

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personne.class);
        Personne personne1 = new Personne();
        personne1.setId(1L);
        Personne personne2 = new Personne();
        personne2.setId(personne1.getId());
        assertThat(personne1).isEqualTo(personne2);
        personne2.setId(2L);
        assertThat(personne1).isNotEqualTo(personne2);
        personne1.setId(null);
        assertThat(personne1).isNotEqualTo(personne2);
    }

    @Test
    public void testConstructor() {
        Personne actualPersonne = new Personne();
        actualPersonne.dateOfBirthday(null);
        actualPersonne.fatherName("Father Name");
        actualPersonne.firstName("Jane");
        actualPersonne.hourOfBithday(null);
        actualPersonne.id(123L);
        actualPersonne.lastName("Doe");
        actualPersonne.motherFirstName("Jane");
        actualPersonne.motherLastName("Doe");
        actualPersonne.numberRegister(1L);
        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName("Department Name");
        department.setDepartmentCode(1);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(1);
        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName("Commune Name");
        commune.communeCode(1);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName("Commune Name");
        commune.setCommuneCode(1);
        commune.setArrondissements(new HashSet<Arrondissement>());
        Arrondissement arrondissement = new Arrondissement();
        arrondissement.setArrondissementCode(1);
        arrondissement.arrondissementCode(1);
        arrondissement.arrondissementName("Arrondissement Name");
        arrondissement.setCommune(commune);
        arrondissement.setCentres(new HashSet<Centre>());
        arrondissement.setId(123L);
        arrondissement.id(123L);
        arrondissement.setArrondissementName("Arrondissement Name");
        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief("Centre Chief");
        centre.setArrondissement(arrondissement);
        centre.setCentreChief("Centre Chief");
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName("Centre Name");
        centre.setCentreName("Centre Name");
        actualPersonne.setCentre(centre);
        actualPersonne.setDateOfBirthday(null);
        actualPersonne.setFatherName("Father Name");
        actualPersonne.setFirstName("Jane");
        actualPersonne.setHourOfBithday(null);
        actualPersonne.setId(123L);
        actualPersonne.setLastName("Doe");
        actualPersonne.setMotherFirstName("Jane");
        actualPersonne.setMotherLastName("Doe");
        actualPersonne.setNumberRegister(1L);
        actualPersonne.setSexe(Sexe.MASCULIN);
        actualPersonne.sexe(Sexe.MASCULIN);
        assertSame(centre, actualPersonne.getCentre());
        assertNull(actualPersonne.getDateOfBirthday());
        assertEquals("Father Name", actualPersonne.getFatherName());
        assertEquals("Jane", actualPersonne.getFirstName());
        assertNull(actualPersonne.getHourOfBithday());
        assertEquals(123L, actualPersonne.getId().longValue());
        assertEquals("Doe", actualPersonne.getLastName());
        assertEquals("Jane", actualPersonne.getMotherFirstName());
        assertEquals("Doe", actualPersonne.getMotherLastName());
        assertEquals(1L, actualPersonne.getNumberRegister().longValue());
        assertEquals(Sexe.MASCULIN, actualPersonne.getSexe());
        assertEquals("Personne{id=123, firstName='Jane', lastName='Doe', fatherName='Father Name', sexe='MASCULIN',"
            + " motherFirstName='Jane', motherLastName='Doe', dateOfBirthday='null', hourOfBithday='null',"
            + " numberRegister=1}", actualPersonne.toString());
    }
}
