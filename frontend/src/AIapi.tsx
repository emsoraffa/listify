const OPEN_AI_URL = "https://api.openai.com/v1/chat/completions"
const OPENAI_API_KEY = process.env.REACT_APP_OPENAI_API_KEY

export const estimateCosts = async (itemNames: string): Promise<string> => {
  const fetchWithRetry = async (retries: number = 3, delay: number = 1000): Promise<string> => {
    try {
      const response = await fetch(OPEN_AI_URL, {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${OPENAI_API_KEY}`,
        },
        body: JSON.stringify({
          model: "gpt-4",
          messages: [
            {
              role: 'user',
              content: `Estimate the total cost of these items in NOK. Provide only the result in the format "Estimated costs: x kr". Do not include any other text or explanations. ITEMS: ${itemNames}`
            }
          ],
          temperature: 0.7
        })
      });

      if (!response.ok) {
        if (response.status === 429 && retries > 0) {
          console.warn('Rate limit hit, retrying...');
          await new Promise(res => setTimeout(res, delay));
          return fetchWithRetry(retries - 1, delay * 2);
        }
        throw new Error(`Network response was not ok: ${response.statusText}`);
      }

      const data = await response.json();
      return data.choices[0].message.content.trim();
    } catch (error) {
      console.error('Error estimating costs:', error);
      throw error;
    }
  };

  return fetchWithRetry();
}


