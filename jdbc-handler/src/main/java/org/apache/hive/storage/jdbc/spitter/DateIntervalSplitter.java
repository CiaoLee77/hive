/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hive.storage.jdbc.spitter;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DateIntervalSplitter implements IntervalSplitter {
  @Override
  public List<MutablePair<String, String>> getIntervals(String lowerBound, String upperBound, int numPartitions, TypeInfo
          typeInfo) {
    List<MutablePair<String, String>> intervals = new ArrayList<>();
    Date dateLower = Date.valueOf(lowerBound);
    Date dateUpper = Date.valueOf(upperBound);
    double dateInterval = (dateUpper.getTime() - dateLower.getTime())/(double)numPartitions;
    Date splitDateLower, splitDateUpper;
    for (int i=0;i<numPartitions;i++) {
      splitDateLower = new Date(Math.round(dateLower.getTime() + dateInterval*i));
      splitDateUpper = new Date(Math.round(dateLower.getTime() + dateInterval*(i+1)));
      if (splitDateLower.compareTo(splitDateUpper) < 0) {
        intervals.add(new MutablePair<String, String>(splitDateLower.toString(), splitDateUpper.toString()));
      }
    }
    return intervals;
  }
}
