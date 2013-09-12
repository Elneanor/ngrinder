/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package net.grinder.util;

import java.util.List;
import java.util.Random;

import net.grinder.common.GrinderProperties;
import net.grinder.script.Grinder;
import net.grinder.script.InternalScriptContext;

import org.ngrinder.common.exception.NGrinderRuntimeException;

/**
 * Convenient NGrinder utilities.
 * 
 * @author JunHo Yoon
 * @since 3.2.3
 */
public abstract class GrinderUtil {
	/**
	 * Get this thread uniq id among all threads in the all agents.
	 * 
	 * @return unique id b/w from 0 to total thread count.
	 * @since 3.2.3
	 */
	public static int getThreadUniqId() {
		InternalScriptContext grinder = getGrinderInstance();
		GrinderProperties properties = grinder.getProperties();
		int totalProcessCount = properties.getInt("grinder.processes", 1);
		int totalThreadCount = properties.getInt("grinder.threads", 1);
		int agentNumber = grinder.getAgentNumber();
		int processNumber = grinder.getProcessNumber();
		int threadNumber = grinder.getThreadNumber();
		// Calc the current thread's unique id
		return (agentNumber * totalProcessCount * totalThreadCount) + (processNumber * totalThreadCount) + threadNumber;
	}

	private static InternalScriptContext getGrinderInstance() {
		InternalScriptContext grinder = Grinder.grinder;
		if (grinder == null || grinder.getThreadNumber() == -1) {
			throw new NGrinderRuntimeException("This method should be called in the worker thread context.");
		}
		return grinder;
	}

	private static Random random = new Random();

	/**
	 * Get the any element from list.
	 * 
	 * @param from
	 *            list
	 * @return any element in the list
	 * @since 3.2.3
	 */
	public static <T> T any(List<T> from) {
		return from.get(random.nextInt(from.size()));
	}

	/**
	 * Get the any element from araay.
	 * 
	 * @param from
	 *            list
	 * @return any element in the list
	 * @since 3.2.3
	 */
	public static <T> T any(T[] from) {
		return from[random.nextInt(from.length)];
	}

	/**
	 * Get the parameter passed by controller. When it's executed in the
	 * validation mode, always returns empty string.
	 * 
	 * @return param. empty string if none.
	 * @since 3.2.3
	 */
	public static String getParam() {
		return getParam("");
	}

	/**
	 * Get the parameter passed by controller. When it's executed in the
	 * validation mode, always returns the given default value.
	 * 
	 * @return param. default value string if the param was not provided.
	 * @since 3.2.3
	 */
	public static String getParam(String defaultValue) {
		return System.getProperty("param", "");
	}

	/**
	 * Get the parameter passed by controller. When it's executed in the
	 * validation mode, always returns the given default value.
	 * 
	 * @return param. default value string if the param was not provided.
	 * @since 3.2.3
	 */
	public static String getAgentCount(String defaultValue) {
		return System.getProperty("param", "");
	}

	/**
	 * Get the total agent count.
	 * 
	 * @return agent count.
	 */
	public static int getAgentCount() {
		return getGrinderInstance().getProperties().getInt("grinder.agents", 1);
	}

	/**
	 * Get the process count per an agent.
	 * 
	 * @return process assigned per an agent
	 */
	public static int getProcessCount() {
		return getGrinderInstance().getProperties().getInt("grinder.processes", 1);
	}

	/**
	 * Get the thread count per a process.
	 * 
	 * @return thread count assigned per a process
	 */
	public static int getThreadCount() {
		return getGrinderInstance().getProperties().getInt("grinder.threads", 1);
	}
}
