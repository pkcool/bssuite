package com.enginemobi.bssuite.web.rest;

import com.enginemobi.bssuite.Application;
import com.enginemobi.bssuite.domain.CalendarItem;
import com.enginemobi.bssuite.repository.CalendarItemRepository;
import com.enginemobi.bssuite.repository.search.CalendarItemSearchRepository;
import com.enginemobi.bssuite.web.rest.dto.CalendarItemDTO;
import com.enginemobi.bssuite.web.rest.mapper.CalendarItemMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.enginemobi.bssuite.domain.enumeration.AlarmType;

/**
 * Test class for the CalendarItemResource REST controller.
 *
 * @see CalendarItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CalendarItemResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final ZonedDateTime DEFAULT_STARTED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_STARTED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_STARTED_ON_STR = dateTimeFormatter.format(DEFAULT_STARTED_ON);

    private static final ZonedDateTime DEFAULT_ENDED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ENDED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ENDED_ON_STR = dateTimeFormatter.format(DEFAULT_ENDED_ON);

    private static final Boolean DEFAULT_IS_ALL_DAY_EVENT = false;
    private static final Boolean UPDATED_IS_ALL_DAY_EVENT = true;

    private static final Boolean DEFAULT_IS_REMAINDER_ENABLED = false;
    private static final Boolean UPDATED_IS_REMAINDER_ENABLED = true;

    private static final ZonedDateTime DEFAULT_REMAINDER_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_REMAINDER_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_REMAINDER_TIME_STR = dateTimeFormatter.format(DEFAULT_REMAINDER_TIME);
    private static final String DEFAULT_WEB_URL = "AAAAA";
    private static final String UPDATED_WEB_URL = "BBBBB";

    private static final Boolean DEFAULT_IS_EDITABLE = false;
    private static final Boolean UPDATED_IS_EDITABLE = true;


    private static final AlarmType DEFAULT_ALARM_TYPE = AlarmType.SOUND;
    private static final AlarmType UPDATED_ALARM_TYPE = AlarmType.EMAIL;

    @Inject
    private CalendarItemRepository calendarItemRepository;

    @Inject
    private CalendarItemMapper calendarItemMapper;

    @Inject
    private CalendarItemSearchRepository calendarItemSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCalendarItemMockMvc;

    private CalendarItem calendarItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CalendarItemResource calendarItemResource = new CalendarItemResource();
        ReflectionTestUtils.setField(calendarItemResource, "calendarItemSearchRepository", calendarItemSearchRepository);
        ReflectionTestUtils.setField(calendarItemResource, "calendarItemRepository", calendarItemRepository);
        ReflectionTestUtils.setField(calendarItemResource, "calendarItemMapper", calendarItemMapper);
        this.restCalendarItemMockMvc = MockMvcBuilders.standaloneSetup(calendarItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        calendarItem = new CalendarItem();
        calendarItem.setTitle(DEFAULT_TITLE);
        calendarItem.setStartedOn(DEFAULT_STARTED_ON);
        calendarItem.setEndedOn(DEFAULT_ENDED_ON);
        calendarItem.setIsAllDayEvent(DEFAULT_IS_ALL_DAY_EVENT);
        calendarItem.setIsRemainderEnabled(DEFAULT_IS_REMAINDER_ENABLED);
        calendarItem.setRemainderTime(DEFAULT_REMAINDER_TIME);
        calendarItem.setWebUrl(DEFAULT_WEB_URL);
        calendarItem.setIsEditable(DEFAULT_IS_EDITABLE);
        calendarItem.setAlarmType(DEFAULT_ALARM_TYPE);
    }

    @Test
    @Transactional
    public void createCalendarItem() throws Exception {
        int databaseSizeBeforeCreate = calendarItemRepository.findAll().size();

        // Create the CalendarItem
        CalendarItemDTO calendarItemDTO = calendarItemMapper.calendarItemToCalendarItemDTO(calendarItem);

        restCalendarItemMockMvc.perform(post("/api/calendarItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(calendarItemDTO)))
                .andExpect(status().isCreated());

        // Validate the CalendarItem in the database
        List<CalendarItem> calendarItems = calendarItemRepository.findAll();
        assertThat(calendarItems).hasSize(databaseSizeBeforeCreate + 1);
        CalendarItem testCalendarItem = calendarItems.get(calendarItems.size() - 1);
        assertThat(testCalendarItem.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCalendarItem.getStartedOn()).isEqualTo(DEFAULT_STARTED_ON);
        assertThat(testCalendarItem.getEndedOn()).isEqualTo(DEFAULT_ENDED_ON);
        assertThat(testCalendarItem.getIsAllDayEvent()).isEqualTo(DEFAULT_IS_ALL_DAY_EVENT);
        assertThat(testCalendarItem.getIsRemainderEnabled()).isEqualTo(DEFAULT_IS_REMAINDER_ENABLED);
        assertThat(testCalendarItem.getRemainderTime()).isEqualTo(DEFAULT_REMAINDER_TIME);
        assertThat(testCalendarItem.getWebUrl()).isEqualTo(DEFAULT_WEB_URL);
        assertThat(testCalendarItem.getIsEditable()).isEqualTo(DEFAULT_IS_EDITABLE);
        assertThat(testCalendarItem.getAlarmType()).isEqualTo(DEFAULT_ALARM_TYPE);
    }

    @Test
    @Transactional
    public void getAllCalendarItems() throws Exception {
        // Initialize the database
        calendarItemRepository.saveAndFlush(calendarItem);

        // Get all the calendarItems
        restCalendarItemMockMvc.perform(get("/api/calendarItems?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(calendarItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].startedOn").value(hasItem(DEFAULT_STARTED_ON_STR)))
                .andExpect(jsonPath("$.[*].endedOn").value(hasItem(DEFAULT_ENDED_ON_STR)))
                .andExpect(jsonPath("$.[*].isAllDayEvent").value(hasItem(DEFAULT_IS_ALL_DAY_EVENT.booleanValue())))
                .andExpect(jsonPath("$.[*].isRemainderEnabled").value(hasItem(DEFAULT_IS_REMAINDER_ENABLED.booleanValue())))
                .andExpect(jsonPath("$.[*].remainderTime").value(hasItem(DEFAULT_REMAINDER_TIME_STR)))
                .andExpect(jsonPath("$.[*].webUrl").value(hasItem(DEFAULT_WEB_URL.toString())))
                .andExpect(jsonPath("$.[*].isEditable").value(hasItem(DEFAULT_IS_EDITABLE.booleanValue())))
                .andExpect(jsonPath("$.[*].alarmType").value(hasItem(DEFAULT_ALARM_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCalendarItem() throws Exception {
        // Initialize the database
        calendarItemRepository.saveAndFlush(calendarItem);

        // Get the calendarItem
        restCalendarItemMockMvc.perform(get("/api/calendarItems/{id}", calendarItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(calendarItem.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.startedOn").value(DEFAULT_STARTED_ON_STR))
            .andExpect(jsonPath("$.endedOn").value(DEFAULT_ENDED_ON_STR))
            .andExpect(jsonPath("$.isAllDayEvent").value(DEFAULT_IS_ALL_DAY_EVENT.booleanValue()))
            .andExpect(jsonPath("$.isRemainderEnabled").value(DEFAULT_IS_REMAINDER_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.remainderTime").value(DEFAULT_REMAINDER_TIME_STR))
            .andExpect(jsonPath("$.webUrl").value(DEFAULT_WEB_URL.toString()))
            .andExpect(jsonPath("$.isEditable").value(DEFAULT_IS_EDITABLE.booleanValue()))
            .andExpect(jsonPath("$.alarmType").value(DEFAULT_ALARM_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCalendarItem() throws Exception {
        // Get the calendarItem
        restCalendarItemMockMvc.perform(get("/api/calendarItems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendarItem() throws Exception {
        // Initialize the database
        calendarItemRepository.saveAndFlush(calendarItem);

		int databaseSizeBeforeUpdate = calendarItemRepository.findAll().size();

        // Update the calendarItem
        calendarItem.setTitle(UPDATED_TITLE);
        calendarItem.setStartedOn(UPDATED_STARTED_ON);
        calendarItem.setEndedOn(UPDATED_ENDED_ON);
        calendarItem.setIsAllDayEvent(UPDATED_IS_ALL_DAY_EVENT);
        calendarItem.setIsRemainderEnabled(UPDATED_IS_REMAINDER_ENABLED);
        calendarItem.setRemainderTime(UPDATED_REMAINDER_TIME);
        calendarItem.setWebUrl(UPDATED_WEB_URL);
        calendarItem.setIsEditable(UPDATED_IS_EDITABLE);
        calendarItem.setAlarmType(UPDATED_ALARM_TYPE);
        CalendarItemDTO calendarItemDTO = calendarItemMapper.calendarItemToCalendarItemDTO(calendarItem);

        restCalendarItemMockMvc.perform(put("/api/calendarItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(calendarItemDTO)))
                .andExpect(status().isOk());

        // Validate the CalendarItem in the database
        List<CalendarItem> calendarItems = calendarItemRepository.findAll();
        assertThat(calendarItems).hasSize(databaseSizeBeforeUpdate);
        CalendarItem testCalendarItem = calendarItems.get(calendarItems.size() - 1);
        assertThat(testCalendarItem.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCalendarItem.getStartedOn()).isEqualTo(UPDATED_STARTED_ON);
        assertThat(testCalendarItem.getEndedOn()).isEqualTo(UPDATED_ENDED_ON);
        assertThat(testCalendarItem.getIsAllDayEvent()).isEqualTo(UPDATED_IS_ALL_DAY_EVENT);
        assertThat(testCalendarItem.getIsRemainderEnabled()).isEqualTo(UPDATED_IS_REMAINDER_ENABLED);
        assertThat(testCalendarItem.getRemainderTime()).isEqualTo(UPDATED_REMAINDER_TIME);
        assertThat(testCalendarItem.getWebUrl()).isEqualTo(UPDATED_WEB_URL);
        assertThat(testCalendarItem.getIsEditable()).isEqualTo(UPDATED_IS_EDITABLE);
        assertThat(testCalendarItem.getAlarmType()).isEqualTo(UPDATED_ALARM_TYPE);
    }

    @Test
    @Transactional
    public void deleteCalendarItem() throws Exception {
        // Initialize the database
        calendarItemRepository.saveAndFlush(calendarItem);

		int databaseSizeBeforeDelete = calendarItemRepository.findAll().size();

        // Get the calendarItem
        restCalendarItemMockMvc.perform(delete("/api/calendarItems/{id}", calendarItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CalendarItem> calendarItems = calendarItemRepository.findAll();
        assertThat(calendarItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
