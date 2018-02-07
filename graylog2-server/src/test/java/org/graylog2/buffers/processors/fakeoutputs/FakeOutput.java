/**
 * This file is part of Graylog.
 *
 * Graylog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.graylog2.buffers.processors.fakeoutputs;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.graylog2.plugin.Message;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.configuration.ConfigurationRequest;
import org.graylog2.plugin.outputs.MessageOutput;
import org.graylog2.plugin.streams.Stream;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author lennart.koopmann
 */
public class FakeOutput implements MessageOutput {
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private int callCount = 0;
    private int writeCount = 0;

    @AssistedInject
    public FakeOutput(@Assisted Stream stream, @Assisted Configuration config) {
        isRunning.set(true);
    }

    @Override
    public void stop() {
        isRunning.set(false);
    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }

    @Override
    public void write(Message message) throws Exception {
        this.callCount++;
        this.writeCount++;
    }

    @Override
    public void write(List<Message> messages) throws Exception {
        for (Message message : messages) {
            write(message);
        }

    }

    public interface Factory extends MessageOutput.Factory<FakeOutput> {
        @Override
        FakeOutput create(Stream stream, Configuration configuration);

        @Override
        Config getConfig();

        @Override
        Descriptor getDescriptor();
    }

    public static class Config extends MessageOutput.Config {
        @Override
        public ConfigurationRequest getRequestedConfiguration() {
            return new ConfigurationRequest();
        }
    }

    public static class Descriptor extends MessageOutput.Descriptor {
        public Descriptor() {
            super("FAKE OUTPUT", false, "", "A fake only output");
        }
    }

    public int getCallCount() {
        return callCount;
    }
    
    public int getWriteCount() {
        return writeCount;
    }
    
}
