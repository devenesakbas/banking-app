
export interface CurrencyResponse {
    success: boolean;
    list: string;
    count: number;
    remaining: number;
    data: Record<string, any>;
}