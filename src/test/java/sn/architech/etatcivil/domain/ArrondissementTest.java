package sn.architech.etatcivil.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import sn.architech.etatcivil.web.rest.TestUtil;

class ArrondissementTest {

    @Test
    public void testCentres() {
        Arrondissement arrondissement = new Arrondissement();
        Arrondissement actualCentresResult = arrondissement.centres(new HashSet<Centre>());
        assertSame(arrondissement, actualCentresResult);
        assertTrue(actualCentresResult.getCentres().isEmpty());
    }

    @Test
    public void testCentres2() {
        Department department = new Department();
        department.departmentName(null);
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName(null);
        department.setDepartmentCode(0);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(0);

        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName(null);
        commune.communeCode(0);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName(null);
        commune.setCommuneCode(0);
        commune.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement = new Arrondissement();
        arrondissement.setArrondissementCode(0);
        arrondissement.arrondissementCode(0);
        arrondissement.arrondissementName(null);
        arrondissement.setCommune(commune);
        arrondissement.setCentres(new HashSet<Centre>());
        arrondissement.setId(123L);
        arrondissement.id(123L);
        arrondissement.setArrondissementName(null);

        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief(null);
        centre.setArrondissement(arrondissement);
        centre.setCentreChief(null);
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName(null);
        centre.setCentreName(null);

        Arrondissement arrondissement1 = new Arrondissement();
        arrondissement1.addCentre(centre);
        Arrondissement actualCentresResult = arrondissement1.centres(new HashSet<Centre>());
        assertSame(arrondissement1, actualCentresResult);
        assertTrue(actualCentresResult.getCentres().isEmpty());
    }

    @Test
    public void testCentres3() {
        Arrondissement arrondissement = new Arrondissement();

        Department department = new Department();
        department.departmentName(null);
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName(null);
        department.setDepartmentCode(0);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(0);

        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName(null);
        commune.communeCode(0);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName(null);
        commune.setCommuneCode(0);
        commune.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement1 = new Arrondissement();
        arrondissement1.setArrondissementCode(0);
        arrondissement1.arrondissementCode(0);
        arrondissement1.arrondissementName(null);
        arrondissement1.setCommune(commune);
        arrondissement1.setCentres(new HashSet<Centre>());
        arrondissement1.setId(123L);
        arrondissement1.id(123L);
        arrondissement1.setArrondissementName(null);

        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief(null);
        centre.setArrondissement(arrondissement1);
        centre.setCentreChief(null);
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName(null);
        centre.setCentreName(null);

        HashSet<Centre> centreSet = new HashSet<Centre>();
        centreSet.add(centre);
        Arrondissement actualCentresResult = arrondissement.centres(centreSet);
        assertSame(arrondissement, actualCentresResult);
        assertEquals(1, actualCentresResult.getCentres().size());
    }

    @Test
    public void testCentres4() {
        Arrondissement arrondissement = new Arrondissement();
        Arrondissement actualCentresResult = arrondissement.centres(new HashSet<Centre>());
        assertSame(arrondissement, actualCentresResult);
        assertTrue(actualCentresResult.getCentres().isEmpty());
    }

    @Test
    public void testCentres5() {
        Department department = new Department();
        department.departmentName(null);
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName(null);
        department.setDepartmentCode(0);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(0);

        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName(null);
        commune.communeCode(0);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName(null);
        commune.setCommuneCode(0);
        commune.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement = new Arrondissement();
        arrondissement.setArrondissementCode(0);
        arrondissement.arrondissementCode(0);
        arrondissement.arrondissementName(null);
        arrondissement.setCommune(commune);
        arrondissement.setCentres(new HashSet<Centre>());
        arrondissement.setId(123L);
        arrondissement.id(123L);
        arrondissement.setArrondissementName(null);

        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief(null);
        centre.setArrondissement(arrondissement);
        centre.setCentreChief(null);
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName(null);
        centre.setCentreName(null);

        Arrondissement arrondissement1 = new Arrondissement();
        arrondissement1.addCentre(centre);
        Arrondissement actualCentresResult = arrondissement1.centres(new HashSet<Centre>());
        assertSame(arrondissement1, actualCentresResult);
        assertTrue(actualCentresResult.getCentres().isEmpty());
    }

    @Test
    public void testCentres6() {
        Arrondissement arrondissement = new Arrondissement();

        Department department = new Department();
        department.departmentName(null);
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName(null);
        department.setDepartmentCode(0);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(0);

        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName(null);
        commune.communeCode(0);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName(null);
        commune.setCommuneCode(0);
        commune.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement1 = new Arrondissement();
        arrondissement1.setArrondissementCode(0);
        arrondissement1.arrondissementCode(0);
        arrondissement1.arrondissementName(null);
        arrondissement1.setCommune(commune);
        arrondissement1.setCentres(new HashSet<Centre>());
        arrondissement1.setId(123L);
        arrondissement1.id(123L);
        arrondissement1.setArrondissementName(null);

        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief(null);
        centre.setArrondissement(arrondissement1);
        centre.setCentreChief(null);
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName(null);
        centre.setCentreName(null);

        HashSet<Centre> centreSet = new HashSet<Centre>();
        centreSet.add(centre);
        Arrondissement actualCentresResult = arrondissement.centres(centreSet);
        assertSame(arrondissement, actualCentresResult);
        assertEquals(1, actualCentresResult.getCentres().size());
    }

    @Test
    public void testAddCentre() {
        Arrondissement arrondissement = new Arrondissement();
        Centre centre = new Centre();
        Arrondissement actualAddCentreResult = arrondissement.addCentre(centre);
        assertSame(arrondissement, actualAddCentreResult);
        assertSame(actualAddCentreResult, centre.getArrondissement());
    }

    @Test
    public void testAddCentre2() {
        Arrondissement arrondissement = new Arrondissement();
        Centre centre = new Centre();
        Arrondissement actualAddCentreResult = arrondissement.addCentre(centre);
        assertSame(arrondissement, actualAddCentreResult);
        assertSame(actualAddCentreResult, centre.getArrondissement());
    }

    @Test
    public void testConstructor2() {
        Arrondissement actualArrondissement = new Arrondissement();
        actualArrondissement.arrondissementCode(1);
        actualArrondissement.arrondissementName("Arrondissement Name");
        actualArrondissement.id(123L);
        actualArrondissement.setArrondissementCode(1);
        actualArrondissement.setArrondissementName("Arrondissement Name");
        Region region = new Region();
        region.regionCode(1);
        region.regionName("us-east-2");
        region.setDepartments(new HashSet<Department>());
        region.setId(123L);
        region.setRegionName("us-east-2");
        region.setRegionCode(1);
        region.id(123L);
        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(region);
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
        actualArrondissement.setCommune(commune);
        actualArrondissement.setId(123L);
        assertEquals(1, actualArrondissement.getArrondissementCode().intValue());
        assertEquals("Arrondissement Name", actualArrondissement.getArrondissementName());
        assertSame(commune, actualArrondissement.getCommune());
        assertEquals(123L, actualArrondissement.getId().longValue());
        assertEquals("Arrondissement{id=123, arrondissementCode=1, arrondissementName='Arrondissement Name'}",
            actualArrondissement.toString());
    }

    @Test
    public void testRemoveCentre() {
        Arrondissement arrondissement = new Arrondissement();
        Centre centre = new Centre();
        assertSame(arrondissement, arrondissement.removeCentre(centre));
        assertNull(centre.getArrondissement());
    }

    @Test
    public void testRemoveCentre2() {
        Arrondissement arrondissement = new Arrondissement();
        Centre centre = new Centre();
        assertSame(arrondissement, arrondissement.removeCentre(centre));
        assertNull(centre.getArrondissement());
    }

    @Test
    public void testSetCentres() {
        Arrondissement arrondissement = new Arrondissement();
        HashSet<Centre> centreSet = new HashSet<Centre>();
        arrondissement.setCentres(centreSet);
        assertSame(centreSet, arrondissement.getCentres());
    }

    @Test
    public void testSetCentres2() {
        Department department = new Department();
        department.departmentName(null);
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName(null);
        department.setDepartmentCode(0);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(0);

        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName(null);
        commune.communeCode(0);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName(null);
        commune.setCommuneCode(0);
        commune.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement = new Arrondissement();
        arrondissement.setArrondissementCode(0);
        arrondissement.arrondissementCode(0);
        arrondissement.arrondissementName(null);
        arrondissement.setCommune(commune);
        arrondissement.setCentres(new HashSet<Centre>());
        arrondissement.setId(123L);
        arrondissement.id(123L);
        arrondissement.setArrondissementName(null);

        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief(null);
        centre.setArrondissement(arrondissement);
        centre.setCentreChief(null);
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName(null);
        centre.setCentreName(null);

        Arrondissement arrondissement1 = new Arrondissement();
        arrondissement1.addCentre(centre);
        HashSet<Centre> centreSet = new HashSet<Centre>();
        arrondissement1.setCentres(centreSet);
        assertSame(centreSet, arrondissement1.getCentres());
    }

    @Test
    public void testSetCentres3() {
        Arrondissement arrondissement = new Arrondissement();

        Department department = new Department();
        department.departmentName(null);
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName(null);
        department.setDepartmentCode(0);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(0);

        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName(null);
        commune.communeCode(0);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName(null);
        commune.setCommuneCode(0);
        commune.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement1 = new Arrondissement();
        arrondissement1.setArrondissementCode(0);
        arrondissement1.arrondissementCode(0);
        arrondissement1.arrondissementName(null);
        arrondissement1.setCommune(commune);
        arrondissement1.setCentres(new HashSet<Centre>());
        arrondissement1.setId(123L);
        arrondissement1.id(123L);
        arrondissement1.setArrondissementName(null);

        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief(null);
        centre.setArrondissement(arrondissement1);
        centre.setCentreChief(null);
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName(null);
        centre.setCentreName(null);

        HashSet<Centre> centreSet = new HashSet<Centre>();
        centreSet.add(centre);
        arrondissement.setCentres(centreSet);
        assertSame(centreSet, arrondissement.getCentres());
    }

    @Test
    public void testSetCentres4() {
        Arrondissement arrondissement = new Arrondissement();
        HashSet<Centre> centreSet = new HashSet<Centre>();
        arrondissement.setCentres(centreSet);
        assertSame(centreSet, arrondissement.getCentres());
    }

    @Test
    public void testSetCentres5() {
        Department department = new Department();
        department.departmentName(null);
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName(null);
        department.setDepartmentCode(0);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(0);

        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName(null);
        commune.communeCode(0);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName(null);
        commune.setCommuneCode(0);
        commune.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement = new Arrondissement();
        arrondissement.setArrondissementCode(0);
        arrondissement.arrondissementCode(0);
        arrondissement.arrondissementName(null);
        arrondissement.setCommune(commune);
        arrondissement.setCentres(new HashSet<Centre>());
        arrondissement.setId(123L);
        arrondissement.id(123L);
        arrondissement.setArrondissementName(null);

        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief(null);
        centre.setArrondissement(arrondissement);
        centre.setCentreChief(null);
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName(null);
        centre.setCentreName(null);

        Arrondissement arrondissement1 = new Arrondissement();
        arrondissement1.addCentre(centre);
        HashSet<Centre> centreSet = new HashSet<Centre>();
        arrondissement1.setCentres(centreSet);
        assertSame(centreSet, arrondissement1.getCentres());
    }

    @Test
    public void testSetCentres6() {
        Arrondissement arrondissement = new Arrondissement();

        Department department = new Department();
        department.departmentName(null);
        department.setRegion(new Region());
        department.setId(123L);
        department.setDepartmentName(null);
        department.setDepartmentCode(0);
        department.setCommunes(new HashSet<Commune>());
        department.id(123L);
        department.departmentCode(0);

        Commune commune = new Commune();
        commune.id(123L);
        commune.communeName(null);
        commune.communeCode(0);
        commune.setId(123L);
        commune.setDepartment(department);
        commune.setCommuneName(null);
        commune.setCommuneCode(0);
        commune.setArrondissements(new HashSet<Arrondissement>());

        Arrondissement arrondissement1 = new Arrondissement();
        arrondissement1.setArrondissementCode(0);
        arrondissement1.arrondissementCode(0);
        arrondissement1.arrondissementName(null);
        arrondissement1.setCommune(commune);
        arrondissement1.setCentres(new HashSet<Centre>());
        arrondissement1.setId(123L);
        arrondissement1.id(123L);
        arrondissement1.setArrondissementName(null);

        Centre centre = new Centre();
        centre.id(123L);
        centre.centreChief(null);
        centre.setArrondissement(arrondissement1);
        centre.setCentreChief(null);
        centre.setId(123L);
        centre.setPersonnes(new HashSet<Personne>());
        centre.centreName(null);
        centre.setCentreName(null);

        HashSet<Centre> centreSet = new HashSet<Centre>();
        centreSet.add(centre);
        arrondissement.setCentres(centreSet);
        assertSame(centreSet, arrondissement.getCentres());
    }

    @Test
    public void testCommune() {
        Arrondissement arrondissement = new Arrondissement();
        Commune commune = new Commune();
        Arrondissement actualCommuneResult = arrondissement.commune(commune);
        assertSame(arrondissement, actualCommuneResult);
        assertSame(commune, actualCommuneResult.getCommune());
    }

    @Test
    public void testCommune2() {
        Arrondissement arrondissement = new Arrondissement();
        Commune commune = new Commune();
        Arrondissement actualCommuneResult = arrondissement.commune(commune);
        assertSame(arrondissement, actualCommuneResult);
        assertSame(commune, actualCommuneResult.getCommune());
    }

    @Test
    public void testEquals() {
        Region region = new Region();
        region.regionCode(1);
        region.regionName("us-east-2");
        region.setDepartments(new HashSet<Department>());
        region.setId(123L);
        region.setRegionName("us-east-2");
        region.setRegionCode(1);
        region.id(123L);

        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(region);
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
        assertFalse(arrondissement.equals(null));
    }

    @Test
    public void testEquals2() {
        Region region = new Region();
        region.regionCode(1);
        region.regionName("us-east-2");
        region.setDepartments(new HashSet<Department>());
        region.setId(123L);
        region.setRegionName("us-east-2");
        region.setRegionCode(1);
        region.id(123L);

        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(region);
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
        assertFalse(arrondissement.equals("Different type to Arrondissement"));
    }

    @Test
    public void testEquals3() {
        Region region = new Region();
        region.regionCode(1);
        region.regionName("us-east-2");
        region.setDepartments(new HashSet<Department>());
        region.setId(123L);
        region.setRegionName("us-east-2");
        region.setRegionCode(1);
        region.id(123L);

        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(region);
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
        assertTrue(arrondissement.equals(arrondissement));
        int expectedHashCodeResult = arrondissement.hashCode();
        assertEquals(expectedHashCodeResult, arrondissement.hashCode());
    }

    @Test
    public void testEquals4() {
        Region region = new Region();
        region.regionCode(1);
        region.regionName("us-east-2");
        region.setDepartments(new HashSet<Department>());
        region.setId(123L);
        region.setRegionName("us-east-2");
        region.setRegionCode(1);
        region.id(123L);

        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(region);
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

        Region region1 = new Region();
        region1.regionCode(1);
        region1.regionName("us-east-2");
        region1.setDepartments(new HashSet<Department>());
        region1.setId(123L);
        region1.setRegionName("us-east-2");
        region1.setRegionCode(1);
        region1.id(123L);

        Department department1 = new Department();
        department1.departmentName("Department Name");
        department1.setRegion(region1);
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
        assertTrue(arrondissement.equals(arrondissement1));
        int expectedHashCodeResult = arrondissement.hashCode();
        assertEquals(expectedHashCodeResult, arrondissement1.hashCode());
    }

    @Test
    public void testEquals5() {
        Region region = new Region();
        region.regionCode(1);
        region.regionName("us-east-2");
        region.setDepartments(new HashSet<Department>());
        region.setId(123L);
        region.setRegionName("us-east-2");
        region.setRegionCode(1);
        region.id(123L);

        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(region);
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
        assertFalse(arrondissement.equals(null));
    }

    @Test
    public void testEquals6() {
        Region region = new Region();
        region.regionCode(1);
        region.regionName("us-east-2");
        region.setDepartments(new HashSet<Department>());
        region.setId(123L);
        region.setRegionName("us-east-2");
        region.setRegionCode(1);
        region.id(123L);

        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(region);
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
        assertFalse(arrondissement.equals("Different type to Arrondissement"));
    }

    @Test
    public void testEquals7() {
        Region region = new Region();
        region.regionCode(1);
        region.regionName("us-east-2");
        region.setDepartments(new HashSet<Department>());
        region.setId(123L);
        region.setRegionName("us-east-2");
        region.setRegionCode(1);
        region.id(123L);

        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(region);
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
        assertTrue(arrondissement.equals(arrondissement));
        int expectedHashCodeResult = arrondissement.hashCode();
        assertEquals(expectedHashCodeResult, arrondissement.hashCode());
    }

    @Test
    public void testEquals8() {
        Region region = new Region();
        region.regionCode(1);
        region.regionName("us-east-2");
        region.setDepartments(new HashSet<Department>());
        region.setId(123L);
        region.setRegionName("us-east-2");
        region.setRegionCode(1);
        region.id(123L);

        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(region);
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

        Region region1 = new Region();
        region1.regionCode(1);
        region1.regionName("us-east-2");
        region1.setDepartments(new HashSet<Department>());
        region1.setId(123L);
        region1.setRegionName("us-east-2");
        region1.setRegionCode(1);
        region1.id(123L);

        Department department1 = new Department();
        department1.departmentName("Department Name");
        department1.setRegion(region1);
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
        assertTrue(arrondissement.equals(arrondissement1));
        int expectedHashCodeResult = arrondissement.hashCode();
        assertEquals(expectedHashCodeResult, arrondissement1.hashCode());
    }

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arrondissement.class);
        Arrondissement arrondissement1 = new Arrondissement();
        arrondissement1.setId(1L);
        Arrondissement arrondissement2 = new Arrondissement();
        arrondissement2.setId(arrondissement1.getId());
        assertThat(arrondissement1).isEqualTo(arrondissement2);
        arrondissement2.setId(2L);
        assertThat(arrondissement1).isNotEqualTo(arrondissement2);
        arrondissement1.setId(null);
        assertThat(arrondissement1).isNotEqualTo(arrondissement2);
    }

    @Test
    public void testConstructor() {
        Arrondissement actualArrondissement = new Arrondissement();
        actualArrondissement.arrondissementCode(1);
        actualArrondissement.arrondissementName("Arrondissement Name");
        actualArrondissement.id(123L);
        actualArrondissement.setArrondissementCode(1);
        actualArrondissement.setArrondissementName("Arrondissement Name");
        Region region = new Region();
        region.regionCode(1);
        region.regionName("us-east-2");
        region.setDepartments(new HashSet<Department>());
        region.setId(123L);
        region.setRegionName("us-east-2");
        region.setRegionCode(1);
        region.id(123L);
        Department department = new Department();
        department.departmentName("Department Name");
        department.setRegion(region);
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
        actualArrondissement.setCommune(commune);
        actualArrondissement.setId(123L);
        assertEquals(1, actualArrondissement.getArrondissementCode().intValue());
        assertEquals("Arrondissement Name", actualArrondissement.getArrondissementName());
        assertSame(commune, actualArrondissement.getCommune());
        assertEquals(123L, actualArrondissement.getId().longValue());
        assertEquals("Arrondissement{id=123, arrondissementCode=1, arrondissementName='Arrondissement Name'}",
            actualArrondissement.toString());
    }
}
