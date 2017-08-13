package com.example.toor.yamblzweather.domain;

import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetricConverter;
import com.example.toor.yamblzweather.domain.utils.TimeUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.GregorianCalendar;

import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.CELSIUS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.IsCloseTo.closeTo;

/**
 * Created by igor on 7/30/17.
 */

@RunWith(PowerMockRunner.class)
public class UtilityTest {
    @Test
    public void KelvinToFahrenheitTest() {
        assertThat(Double.valueOf(TemperatureMetricConverter.getSupportedTemperature(300.0, TemperatureMetric.FAHRENHEIT)),
                closeTo(80, 1));
    }

    @Test
    public void KelvinToCelsiusTest() {
        assertThat(Double.valueOf(TemperatureMetricConverter.getSupportedTemperature(300.0, CELSIUS)),
                closeTo(27, 1));
    }

    @Test
    public void TemperatureFromStringTest() {
        assertThat(TemperatureMetric.fromString("metric"), equalTo(CELSIUS));
    }

    @Test
    public void ShortDayTest() {
        long time = 1502653271L;
        assertThat(TimeUtils.formatDayShort(time), equalTo("Sun"));
    }

    @Test
    public void ShortDayAndDateTest() {
        long time = 1502653271L;
        assertThat(TimeUtils.formatDayAndDateShort(time), equalTo("13 Sun"));
    }
}
