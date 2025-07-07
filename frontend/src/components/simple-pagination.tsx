import { SpringBootPagination } from "@/domain/domain";
import { Button } from "./ui/button";
import { ChevronLeft, ChevronRight } from "lucide-react";

// The props interface is perfect as is.
interface SimplePaginationProps<T> {
  pagination: SpringBootPagination<T>;
  onPageChange: (page: number) => void;
}

// The fix is in the function signature below.
export function SimplePagination<T>({
  pagination,
  onPageChange,
}: SimplePaginationProps<T>) { // <-- CORRECTED LINE
  
  // The rest of your component logic is correct.
  const currentPage = pagination.number;
  const totalPages = pagination.totalPages;

  return (
    <div className="flex gap-2 items-center">
      <Button
        size="sm"
        className="cursor-pointer"
        onClick={() => onPageChange(currentPage - 1)}
        disabled={pagination.first}
      >
        <ChevronLeft className="h-4 w-4" />
        <span className="sr-only">Previous Page</span>
      </Button>
      <div className="text-sm">
        Page {currentPage + 1} of {totalPages}
      </div>
      <Button
        size="sm"
        className="cursor-pointer"
        onClick={() => onPageChange(currentPage + 1)}
        disabled={pagination.last}
      >
        <ChevronRight className="h-4 w-4" />
        <span className="sr-only">Next Page</span>
      </Button>
    </div>
  );
}