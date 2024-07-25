export type DashboardListDto = {
  list_id: number,
  list_name: string,
  author: string,
  list_items: CheckListItemDto[],
}

export type ListDto = {
  id: number | null;
  list_name: string,
  list_items: CheckListItemDto[],
}

export type CheckListItemDto = {
  id: number | null;
  text: string;
  checked: boolean;
}

export type UserDto = {
  name: string;
  email: string;
}
