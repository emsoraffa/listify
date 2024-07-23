export type DashboardListDto = {
  list_id: number,
  list_name: string,
  author: string,
  list_items: CheckListItemDto[],

}

export type ListDto = {
  id: number;
  list_name: string,
  list_items: CheckListItemDto[],
}

export type CheckListItemDto = {
  text: string,
  checked: boolean,
}
