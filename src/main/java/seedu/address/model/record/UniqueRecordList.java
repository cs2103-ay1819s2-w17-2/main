package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of Records that enforces uniqueness between its elements and does not allow nulls.
 * A Record is considered unique by comparing using {@code Record#equals(Record)}. As such, adding and updating of
 * Record uses Record#equals(Record) for equality so as to ensure that the Record being added or updated is
 * unique in terms of identity in the UniqueRecordList. However, the removal of a Record uses Record#equals(Record)
 * so as to ensure that the Record with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Record#equals(Object).
 */
public class UniqueRecordList implements Iterable<Record> {

    private final ObservableList<Record> internalList = FXCollections.observableArrayList();
    private final ObservableList<Record> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Task as the given argument.
     */
    public boolean contains(Record toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a Task to the list.
     * The Task must not already exist in the list.
     */
    public void add(Record toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent Task from the list.
     * The Task must exist in the list.
     */
    public void remove(Record toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code Tasks}.
     * {@code Tasks} must not contain duplicate Tasks.
     */
    public void setRecords(List<Record> records) {
        requireAllNonNull(records);
        internalList.setAll(records);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Record> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Record> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRecordList // instanceof handles nulls
                && internalList.equals(((UniqueRecordList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}