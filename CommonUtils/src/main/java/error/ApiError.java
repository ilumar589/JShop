package error;

import org.eclipse.collections.api.list.ImmutableList;

public record ApiError(int code, String status, ImmutableList<ApErrorDetail> details) {
}
