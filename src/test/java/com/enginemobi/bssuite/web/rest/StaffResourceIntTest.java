package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.Staff;
import com.enginemobi.bssuite.repository.StaffRepository;
import com.enginemobi.bssuite.repository.search.StaffSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.StaffDTO;
import com.enginemobi.bssuite.web.rest.mapper.StaffMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the StaffResource REST controller.
 *
 * @see StaffResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StaffResourceIntTest {

    private static final String DEFAULT_CODE = "AA";
    private static final String UPDATED_CODE = "BB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    private static final Double DEFAULT_COMMISSION = 1D;
    private static final Double UPDATED_COMMISSION = 2D;
    private static final String DEFAULT_OCCUPATION = "AAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBB";
    private static final String DEFAULT_WORK_PHONE = "AAAAA";
    private static final String UPDATED_WORK_PHONE = "BBBBB";
    private static final String DEFAULT_HOME_PHONE = "AAAAA";
    private static final String UPDATED_HOME_PHONE = "BBBBB";
    private static final String DEFAULT_WORK_MOBILE = "AAAAA";
    private static final String UPDATED_WORK_MOBILE = "BBBBB";
    private static final String DEFAULT_HOME_MOBILE = "AAAAA";
    private static final String UPDATED_HOME_MOBILE = "BBBBB";
    private static final String DEFAULT_WEB_EMAIL = "AAAAA";
    private static final String UPDATED_WEB_EMAIL = "BBBBB";
    private static final String DEFAULT_HOME_EMAIL = "AAAAA";
    private static final String UPDATED_HOME_EMAIL = "BBBBB";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_LOCKED_TO_STORE = false;
    private static final Boolean UPDATED_IS_LOCKED_TO_STORE = true;

    private static final Boolean DEFAULT_IS_TECHNICAL = false;
    private static final Boolean UPDATED_IS_TECHNICAL = true;

    @Inject
    private StaffRepository staffRepository;

    @Inject
    private StaffMapper staffMapper;

    @Inject
    private StaffSearchRepository staffSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStaffMockMvc;

    private Staff staff;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StaffResource staffResource = new StaffResource();
        ReflectionTestUtils.setField(staffResource, "staffSearchRepository", staffSearchRepository);
        ReflectionTestUtils.setField(staffResource, "staffRepository", staffRepository);
        ReflectionTestUtils.setField(staffResource, "staffMapper", staffMapper);
        this.restStaffMockMvc = MockMvcBuilders.standaloneSetup(staffResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        staff = new Staff();
        staff.setCode(DEFAULT_CODE);
        staff.setName(DEFAULT_NAME);
        staff.setComment(DEFAULT_COMMENT);
        staff.setCommission(DEFAULT_COMMISSION);
        staff.setOccupation(DEFAULT_OCCUPATION);
        staff.setWorkPhone(DEFAULT_WORK_PHONE);
        staff.setHomePhone(DEFAULT_HOME_PHONE);
        staff.setWorkMobile(DEFAULT_WORK_MOBILE);
        staff.setHomeMobile(DEFAULT_HOME_MOBILE);
        staff.setWebEmail(DEFAULT_WEB_EMAIL);
        staff.setHomeEmail(DEFAULT_HOME_EMAIL);
        staff.setBirthday(DEFAULT_BIRTHDAY);
        staff.setIsLockedToStore(DEFAULT_IS_LOCKED_TO_STORE);
        staff.setIsTechnical(DEFAULT_IS_TECHNICAL);
    }

    @Test
    @Transactional
    public void createStaff() throws Exception {
        int databaseSizeBeforeCreate = staffRepository.findAll().size();

        // Create the Staff
        StaffDTO staffDTO = staffMapper.staffToStaffDTO(staff);

        restStaffMockMvc.perform(post("/api/staffs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(staffDTO)))
                .andExpect(status().isCreated());

        // Validate the Staff in the database
        List<Staff> staffs = staffRepository.findAll();
        assertThat(staffs).hasSize(databaseSizeBeforeCreate + 1);
        Staff testStaff = staffs.get(staffs.size() - 1);
        assertThat(testStaff.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStaff.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStaff.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testStaff.getCommission()).isEqualTo(DEFAULT_COMMISSION);
        assertThat(testStaff.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testStaff.getWorkPhone()).isEqualTo(DEFAULT_WORK_PHONE);
        assertThat(testStaff.getHomePhone()).isEqualTo(DEFAULT_HOME_PHONE);
        assertThat(testStaff.getWorkMobile()).isEqualTo(DEFAULT_WORK_MOBILE);
        assertThat(testStaff.getHomeMobile()).isEqualTo(DEFAULT_HOME_MOBILE);
        assertThat(testStaff.getWebEmail()).isEqualTo(DEFAULT_WEB_EMAIL);
        assertThat(testStaff.getHomeEmail()).isEqualTo(DEFAULT_HOME_EMAIL);
        assertThat(testStaff.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testStaff.getIsLockedToStore()).isEqualTo(DEFAULT_IS_LOCKED_TO_STORE);
        assertThat(testStaff.getIsTechnical()).isEqualTo(DEFAULT_IS_TECHNICAL);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffRepository.findAll().size();
        // set the field null
        staff.setCode(null);

        // Create the Staff, which fails.
        StaffDTO staffDTO = staffMapper.staffToStaffDTO(staff);

        restStaffMockMvc.perform(post("/api/staffs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(staffDTO)))
                .andExpect(status().isBadRequest());

        List<Staff> staffs = staffRepository.findAll();
        assertThat(staffs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStaffs() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        // Get all the staffs
        restStaffMockMvc.perform(get("/api/staffs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(staff.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION.doubleValue())))
                .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
                .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE.toString())))
                .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE.toString())))
                .andExpect(jsonPath("$.[*].workMobile").value(hasItem(DEFAULT_WORK_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].homeMobile").value(hasItem(DEFAULT_HOME_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].webEmail").value(hasItem(DEFAULT_WEB_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].homeEmail").value(hasItem(DEFAULT_HOME_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
                .andExpect(jsonPath("$.[*].isLockedToStore").value(hasItem(DEFAULT_IS_LOCKED_TO_STORE.booleanValue())))
                .andExpect(jsonPath("$.[*].isTechnical").value(hasItem(DEFAULT_IS_TECHNICAL.booleanValue())));
    }

    @Test
    @Transactional
    public void getStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        // Get the staff
        restStaffMockMvc.perform(get("/api/staffs/{id}", staff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(staff.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.commission").value(DEFAULT_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.workPhone").value(DEFAULT_WORK_PHONE.toString()))
            .andExpect(jsonPath("$.homePhone").value(DEFAULT_HOME_PHONE.toString()))
            .andExpect(jsonPath("$.workMobile").value(DEFAULT_WORK_MOBILE.toString()))
            .andExpect(jsonPath("$.homeMobile").value(DEFAULT_HOME_MOBILE.toString()))
            .andExpect(jsonPath("$.webEmail").value(DEFAULT_WEB_EMAIL.toString()))
            .andExpect(jsonPath("$.homeEmail").value(DEFAULT_HOME_EMAIL.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.isLockedToStore").value(DEFAULT_IS_LOCKED_TO_STORE.booleanValue()))
            .andExpect(jsonPath("$.isTechnical").value(DEFAULT_IS_TECHNICAL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStaff() throws Exception {
        // Get the staff
        restStaffMockMvc.perform(get("/api/staffs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

		int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Update the staff
        staff.setCode(UPDATED_CODE);
        staff.setName(UPDATED_NAME);
        staff.setComment(UPDATED_COMMENT);
        staff.setCommission(UPDATED_COMMISSION);
        staff.setOccupation(UPDATED_OCCUPATION);
        staff.setWorkPhone(UPDATED_WORK_PHONE);
        staff.setHomePhone(UPDATED_HOME_PHONE);
        staff.setWorkMobile(UPDATED_WORK_MOBILE);
        staff.setHomeMobile(UPDATED_HOME_MOBILE);
        staff.setWebEmail(UPDATED_WEB_EMAIL);
        staff.setHomeEmail(UPDATED_HOME_EMAIL);
        staff.setBirthday(UPDATED_BIRTHDAY);
        staff.setIsLockedToStore(UPDATED_IS_LOCKED_TO_STORE);
        staff.setIsTechnical(UPDATED_IS_TECHNICAL);
        StaffDTO staffDTO = staffMapper.staffToStaffDTO(staff);

        restStaffMockMvc.perform(put("/api/staffs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(staffDTO)))
                .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffs = staffRepository.findAll();
        assertThat(staffs).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffs.get(staffs.size() - 1);
        assertThat(testStaff.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStaff.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStaff.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testStaff.getCommission()).isEqualTo(UPDATED_COMMISSION);
        assertThat(testStaff.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testStaff.getWorkPhone()).isEqualTo(UPDATED_WORK_PHONE);
        assertThat(testStaff.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testStaff.getWorkMobile()).isEqualTo(UPDATED_WORK_MOBILE);
        assertThat(testStaff.getHomeMobile()).isEqualTo(UPDATED_HOME_MOBILE);
        assertThat(testStaff.getWebEmail()).isEqualTo(UPDATED_WEB_EMAIL);
        assertThat(testStaff.getHomeEmail()).isEqualTo(UPDATED_HOME_EMAIL);
        assertThat(testStaff.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testStaff.getIsLockedToStore()).isEqualTo(UPDATED_IS_LOCKED_TO_STORE);
        assertThat(testStaff.getIsTechnical()).isEqualTo(UPDATED_IS_TECHNICAL);
    }

    @Test
    @Transactional
    public void deleteStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

		int databaseSizeBeforeDelete = staffRepository.findAll().size();

        // Get the staff
        restStaffMockMvc.perform(delete("/api/staffs/{id}", staff.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Staff> staffs = staffRepository.findAll();
        assertThat(staffs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
