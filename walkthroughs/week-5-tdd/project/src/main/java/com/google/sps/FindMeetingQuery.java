// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class FindMeetingQuery {

    private final int DURATION_1_DAY = TimeRange.WHOLE_DAY.duration();

    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        long duration = request.getDuration();
        Collection<String> meetingAttendees = request.getAttendees();
        Collection<String> meetingOptionalAttendees = request.getOptionalAttendees();

        if (duration > DURATION_1_DAY) {
            return Arrays.asList();
        }

        if (meetingAttendees.isEmpty()) {
            if (meetingOptionalAttendees.isEmpty()) {
                return Arrays.asList(TimeRange.WHOLE_DAY);
            } else {
                Collection<TimeRange> optionalConflicts = findConflicts(events, meetingOptionalAttendees);
                return findPossibilities(optionalConflicts, meetingOptionalAttendees, duration);
            }
        }
        
        Collection<TimeRange> requiredConflicts = findConflicts(events, meetingAttendees);
        Collection<TimeRange> requiredPossibilites = findPossibilities(requiredConflicts, meetingAttendees, duration);

        // Remove the times that optional attendees can't attend, if they can attend at least one possibility.
        Collection<TimeRange> optionalConflicts = findConflicts(events, meetingOptionalAttendees);
        Collection<TimeRange> removeConflicts = new LinkedList<>();

        for (TimeRange conflict : optionalConflicts) {
            for (TimeRange possibility : requiredPossibilites) {
                if (conflict.overlaps(possibility)) {
                    removeConflicts.add(possibility);
                }
            }
        }

        if (removeConflicts.size() == requiredPossibilites.size()) {
            return requiredPossibilites;
        } else {
            requiredPossibilites.removeAll(removeConflicts);
            return requiredPossibilites;
        }
    }

    private Collection<TimeRange> findConflicts(Collection<Event> events, Collection<String> attendees) {
        // Use a LinkedList to preserve order.
        Collection<TimeRange> conflicts = new LinkedList<>();
         for (Event event : events) {
            Set<String> eventAttendees = event.getAttendees();
            TimeRange timeRange = event.getWhen();
            if (!Collections.disjoint(eventAttendees, attendees)) {
                conflicts.add(timeRange);
            }
        }   

        return conflicts;    
    }

    private Collection<TimeRange> findPossibilities(Collection<TimeRange> conflicts, Collection<String> attendees, long duration) {
        // Use a LinkedList to preserve order.
        Collection<TimeRange> possibilities = new LinkedList<>();

        // Variables represening the start and end time for possible meeting slots.
        int possibleStartTime = TimeRange.START_OF_DAY;

        for (TimeRange conflict : conflicts) {
            int conflictStartTime = conflict.start();
            int conflictEndTime = conflict.end();

            // If the current conflict overlaps with another, make the next possible start time
            // either the end time of this conflict, or the end time of the last conflict.
            if (conflictStartTime < possibleStartTime) {
                possibleStartTime  = Math.max(conflictEndTime, possibleStartTime);
                continue;
            }

            // Create a possible open block of time if the difference between the possible start
            // time and the time of the next conflict is >= the meeting duration.
            if (conflictStartTime - possibleStartTime >= duration) {
                TimeRange possibility = TimeRange.fromStartEnd(possibleStartTime, conflictStartTime, false);
                possibilities.add(possibility);
            } 

            // The next possible meeting time can start at the end of the current conflict.
            possibleStartTime = conflictEndTime;
        }

        if (TimeRange.END_OF_DAY - possibleStartTime >= duration) {
            TimeRange endOfDayPossibility = TimeRange.fromStartEnd(possibleStartTime, TimeRange.END_OF_DAY, true);
            possibilities.add(endOfDayPossibility);
        }
        
        return possibilities;        
    }
}
