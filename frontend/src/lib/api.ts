import {
  CreateEventRequest,
  EventDetails,
  EventSummary,
  isErrorResponse,
  PublishedEventDetails,
  PublishedEventSummary,
  SpringBootPagination,
  TicketDetails,
  TicketSummary,
  TicketValidationRequest,
  TicketValidationResponse,
  UpdateEventRequest,
} from "@/domain/domain";

// --- Helper Function for API Calls ---

interface FetchOptions extends RequestInit {
  accessToken?: string;
}

/**
 * A wrapper around the fetch API that handles standard headers,
 * authentication, JSON parsing, and robust error handling.
 *
 * @param endpoint The API endpoint to call (e.g., '/api/v1/events')
 * @param options Fetch options, including an optional accessToken.
 * @returns A promise that resolves with the parsed JSON response.
 */
const apiFetch = async <T>(endpoint: string, options: FetchOptions = {}): Promise<T> => {
  const { accessToken, ...fetchOptions } = options;

  // Declaratively build the headers object. This is type-safe and clean.
  // It conditionally adds the Authorization header only if an accessToken is provided.
  const headers: HeadersInit = {
    "Content-Type": "application/json",
    ...fetchOptions.headers,
    ...(accessToken && { Authorization: `Bearer ${accessToken}` }),
  };

  const response = await fetch(endpoint, {
    ...fetchOptions,
    headers,
  });

  // --- Error Handling ---
  // First, check if the response was successful. If not, parse the error.
  if (!response.ok) {
    let errorMessage = `Request failed with status ${response.status}`;
    try {
      // Try to parse a specific JSON error message from the backend.
      const errorBody = await response.json();
      if (isErrorResponse(errorBody)) {
        errorMessage = errorBody.error;
      }
    } catch (e) {
      // If the error response wasn't JSON, the generic status message is used.
      console.error("Could not parse error response body as JSON.", e);
    }
    throw new Error(errorMessage);
  }

  // --- Success Handling ---

  // Handle successful responses that have no content (e.g., for DELETE or some POSTs).
  if (response.status === 204) {
    return undefined as T;
  }

  // Handle binary data like images/blobs.
  const contentType = response.headers.get("Content-Type");
  if (contentType && (contentType.startsWith("image/") || contentType === "application/octet-stream")) {
    return response.blob() as Promise<T>;
  }

  // For all other successful responses, parse and return the JSON body.
  return response.json() as Promise<T>;
};

// --- Refactored API Functions ---

export const createEvent = (accessToken: string, request: CreateEventRequest): Promise<void> =>
  apiFetch(`/api/v1/events`, {
    method: "POST",
    accessToken,
    body: JSON.stringify(request),
  });

export const updateEvent = (accessToken: string, id: string, request: UpdateEventRequest): Promise<void> =>
  apiFetch(`/api/v1/events/${id}`, {
    method: "PUT",
    accessToken,
    body: JSON.stringify(request),
  });

export const listEvents = (accessToken: string, page: number): Promise<SpringBootPagination<EventSummary>> =>
  apiFetch(`/api/v1/events?page=${page}&size=2`, {
    accessToken,
  });

export const getEvent = (accessToken: string, id: string): Promise<EventDetails> =>
  apiFetch(`/api/v1/events/${id}`, {
    accessToken,
  });

export const deleteEvent = (accessToken: string, id: string): Promise<void> =>
  apiFetch(`/api/v1/events/${id}`, {
    method: "DELETE",
    accessToken,
  });

export const listPublishedEvents = (page: number): Promise<SpringBootPagination<PublishedEventSummary>> =>
  apiFetch(`/api/v1/published-events?page=${page}&size=4`);

export const searchPublishedEvents = (query: string, page: number): Promise<SpringBootPagination<PublishedEventSummary>> =>
  apiFetch(`/api/v1/published-events?q=${query}&page=${page}&size=4`);

export const getPublishedEvent = (id: string): Promise<PublishedEventDetails> =>
  apiFetch(`/api/v1/published-events/${id}`);

export const purchaseTicket = (accessToken: string, eventId: string, ticketTypeId: string): Promise<void> =>
  apiFetch(`/api/v1/events/${eventId}/ticket-types/${ticketTypeId}/tickets`, {
    method: "POST",
    accessToken,
  });

export const listTickets = (accessToken: string, page: number): Promise<SpringBootPagination<TicketSummary>> =>
  apiFetch(`/api/v1/tickets?page=${page}&size=8`, {
    accessToken,
  });

export const getTicket = (accessToken: string, id: string): Promise<TicketDetails> =>
  apiFetch(`/api/v1/tickets/${id}`, {
    accessToken,
  });

export const getTicketQr = (accessToken: string, id: string): Promise<Blob> =>
  apiFetch(`/api/v1/tickets/${id}/qr-codes`, {
    accessToken,
  });

export const validateTicket = (accessToken: string, request: TicketValidationRequest): Promise<TicketValidationResponse> =>
  apiFetch(`/api/v1/ticket-validations`, {
    method: "POST",
    accessToken,
    body: JSON.stringify(request),
  });