// src/pages/AttendeeLandingPage.tsx

import { useAuth } from "react-oidc-context";
import { Button } from "../components/ui/button";
import { useNavigate } from "react-router";
import { Input } from "@/components/ui/input";
import { AlertCircle, Search } from "lucide-react";
import { useEffect, useState } from "react";
import { PublishedEventSummary, SpringBootPagination } from "@/domain/domain";
import { listPublishedEvents, searchPublishedEvents } from "@/lib/api";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import PublishedEventCard from "@/components/published-event-card";
import { SimplePagination } from "@/components/simple-pagination"; // Assuming this is the correct path

const AttendeeLandingPage: React.FC = () => {
  const { isAuthenticated, isLoading, signinRedirect, signoutRedirect } =
    useAuth();
  const navigate = useNavigate();

  const [page, setPage] = useState(0);
  const [inputValue, setInputValue] = useState("");
  const [query, setQuery] = useState("");
  const [publishedEvents, setPublishedEvents] = useState<
    SpringBootPagination<PublishedEventSummary> | undefined
  >();
  const [error, setError] = useState<string | undefined>();

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        setError(undefined);
        let result;
        // This check prevents the TypeScript error by ensuring 'query' is a valid string.
        if (query.trim().length > 0) {
          result = await searchPublishedEvents(query, page);
        } else {
          result = await listPublishedEvents(page);
        }
        setPublishedEvents(result);
      } catch (err) {
        const errorMessage =
          err instanceof Error ? err.message : "An unknown error has occurred";
        setError(errorMessage);
      }
    };

    fetchEvents();
  }, [query, page]);

  const handleSearch = () => {
    setQuery(inputValue);
    setPage(0);
  };

  const handleKeyPress = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      handleSearch();
    }
  };

  if (error) {
    return (
      <div className="min-h-screen bg-black text-white p-4">
        <Alert variant="destructive" className="bg-gray-900 border-red-700">
          <AlertCircle className="h-4 w-4" />
          <AlertTitle>Error</AlertTitle>
          <AlertDescription>{error}</AlertDescription>
        </Alert>
      </div>
    );
  }

  if (isLoading) {
    return <div className="min-h-screen bg-black text-white"><p>Loading...</p></div>;
  }

  return (
    <div className="bg-black min-h-screen text-white">
      {/* Nav */}
      <div className="flex justify-end p-4 container mx-auto">
        {isAuthenticated ? (
          <div className="flex gap-4">
            <Button onClick={() => navigate("/dashboard")}>Dashboard</Button>
            <Button onClick={() => signoutRedirect()}>Log out</Button>
          </div>
        ) : (
          <Button onClick={() => signinRedirect()}>Log in</Button>
        )}
      </div>

      {/* Hero */}
      <div className="container mx-auto px-4 mb-8">
        <div className="bg-[url(/organizers-landing-hero.png)] bg-cover min-h-[200px] rounded-lg bg-bottom md:min-h-[250px]">
          <div className="bg-black/45 min-h-[200px] md:min-h-[250px] p-15 md:p-20 flex flex-col justify-center">
            <h1 className="text-2xl font-bold mb-4">
              Find Tickets to Your Next Event
            </h1>
            <div className="flex gap-2 max-w-lg">
              <Input
                className="bg-white text-black"
                placeholder="Search by event name, venue, etc..."
                value={inputValue}
                onChange={(e) => setInputValue(e.target.value)}
                onKeyDown={handleKeyPress}
              />
              <Button onClick={handleSearch}>
                <Search />
              </Button>
            </div>
          </div>
        </div>
      </div>

      {/* Published Event Cards */}
      <div className="container mx-auto px-4">
        {publishedEvents?.content && publishedEvents.content.length > 0 ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
            {publishedEvents.content.map((publishedEvent) => (
              <PublishedEventCard
                publishedEvent={publishedEvent}
                key={publishedEvent.id}
              />
            ))}
          </div>
        ) : (
          <div className="text-center py-16">
            <p className="text-gray-400">No events found.</p>
          </div>
        )}
      </div>

      {/* Pagination */}
      {/* This check for 'publishedEvents' ensures you don't pass 'undefined' to the component */}
      {publishedEvents && !publishedEvents.empty && (
        <div className="w-full flex justify-center py-8">
          <SimplePagination
            pagination={publishedEvents}
            onPageChange={setPage}
          />
        </div>
      )}
    </div>
  );
};

export default AttendeeLandingPage;