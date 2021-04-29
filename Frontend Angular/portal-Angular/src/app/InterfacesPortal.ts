export interface ForumSection {
  id: number;
  section: string;
  forumSubSectionsList: ForumSubSections[];
}

export interface ForumSubSections {
  id: number;
  name: string;
  forumTopicList: ForumTopic[];
}

export interface ForumTopic {
  id: number;
  titleTopic: string;
  date: string;
  forumTopicCommList: ForumTopicComm[];
}

export interface ForumTopicComm {
  id: number;
  author: string;
  text: string;
  date: string;
  user: Users;
}

export interface News {
  id: number;
  title: string;
  image: string;
  username: string;
  article: string;
  date: string;
  commentsSet: Comments[];
  ratings: Rating[];
}
export interface NewsDTO {
  id: number;
  title: string;
  image: string;
  username: string;
  article: string;
  date: string;
  ratings: number;
}

export interface Rating {
  id: Id;
  rating: number;
}

export interface Id {
  userId: number;
  articleId: number;
}


export interface Comments {
  id: number;
  text: string;
  date: string;
  user: Users[];
}

export interface Users {
  id: number;
  username: string;
  email: string;
  password: string;
  role: string;
  commentsSet: any[];
  newsSet: News[];
  forumTopicsSet: ForumTopic[];
  forumTopicCommSet: ForumTopicComm[];
}

export interface UsersDTO {
  username: string;
  email: string;
  password: string;

}

export interface UsersListDTO {
  id: number;
  username: string;
  email: string;

}

export interface Hardware {
  id: number;
  name: string;
}

export interface Role {
  role: string;
}

export interface Gpu {
  id: number;
  manufacture: string;
  brand: string;
  type: string;
  tdp: string;
  recommend: boolean;
}

export interface Cpu {
  id: number;
  brand: string;
  type?: any;
  socket?: any;
  recommend: boolean;
}
