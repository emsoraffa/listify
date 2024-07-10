export type DashboardListDto = {
  list_name: string,
  author: string,
  listitems: CheckListItemDto[],

}

export type ListDto = {
  id: number;
  list_name: string,
  listitems: CheckListItemDto[],
}

export type CheckListItemDto = {
  text: string,
  checked: boolean,
}
